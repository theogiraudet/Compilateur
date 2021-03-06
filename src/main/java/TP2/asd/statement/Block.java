package TP2.asd.statement;

import TP2.Context;
import TP2.llvm.Llvm;
import TP2.utils.Try;
import TP2.utils.Utils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Block implements Statement {

    private final List<Statement> statements;
    private final List<Declaration> declarations;

    public Block(List<Declaration> ceclarations, List<Statement> statements) {
        this.statements = statements;
        this.declarations = ceclarations;
    }

    @Override
    public String pp(int nbIndent) {
        return Utils.indent(nbIndent - 1) + "{\n" +
                declarations.stream().map(d -> d.pp(nbIndent)).collect(Collectors.joining("\n")) +
                statements.stream().map(s -> s.pp(nbIndent)).collect(Collectors.joining("\n")) +
                "\n" + Utils.indent(nbIndent - 1) + "}";
    }

    @Override
    public Llvm.IR toIr(Context table) throws RuntimeException {
        // Nouvelle table de contexte
        final Context newTable = new Context(table);

        final Optional<Try<Llvm.IR>> dec =
                    declarations.stream()
                    .map(s -> Try.tryThis(() -> s.toIR(newTable))) // Application de la fonction toIr sur chacune des déclarations
                    .reduce((acc, ir) -> acc.reduce(ir, Llvm.IR::append)); // Réduction du stream en concaténant toutes les instructions

        // Un bloc ne pouvant pas ne pas avoir de statements, ins ne sera jamais vide
        final Llvm.IR ins = Try.toTry( // Conversion du Optional<Try<Llvm.IR>> en Try<Try<Llvm.IR>>
                statements.stream()
                        .map(s -> Try.tryThis(() -> s.toIr(newTable))) // Application de la fonction toIr sur chacune des déclarations
                        .reduce((acc, ir) -> acc.reduce(ir, Llvm.IR::append))) // Réduction du stream en concaténant toutes les instructions
                .flatMap(i -> i) // Conversion du Try<Try<Llvm.IR>> (à cause de la conversion de Optional à Try) en Try<Llvm.IR>
                .get();

        // Traitement des exceptions sur dec
        if(dec.isPresent() && dec.get().failed())
            dec.get().get();

        //Si dec existe, alors on append ins, sinon, on retourne directement ins
        return dec.map(ir -> ir.get().append(ins)).orElse(ins);
    }
}

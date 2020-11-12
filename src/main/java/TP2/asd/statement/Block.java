package TP2.asd.statement;

import TP2.SymbolTable;
import TP2.TypeException;
import TP2.llvm.Llvm;
import TP2.utils.Try;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Block implements Statement {

    private final List<Statement> statements;
    private final List<Declaration> declarations;

    public Block(List<Declaration> declarations, List<Statement> statements) {
        this.statements = statements;
        this.declarations = declarations;
    }

    @Override
    public String pp() {
        return "{\n" +
                statements.stream().map(Statement::pp).collect(Collectors.joining("\n\t")) +
                "\n}";
    }

    @Override
    public Llvm.IR toIR(SymbolTable table) throws RuntimeException {
        // Nouvelle table de contexte
        final SymbolTable newTable = new SymbolTable(table);

        /*final Optional<Llvm.IR> dec = declarations.stream()
                .map(s -> errorWrapper(s::toIR, newTable))
                .reduce((acc, ir) -> acc.append(ir));*/

        final Optional<Try<Llvm.IR>> dec =
                    declarations.stream()
                    .map(s -> Try.tryThis(() -> s.toIR(newTable))) // Application de la fonction toIr sur chacune des déclarations
                    .reduce((acc, ir) -> acc.reduce(ir, Llvm.IR::append)); // Réduction du stream en concaténant toutes les instructions

        // Un bloc ne pouvant pas ne pas avoir de statements, ins ne sera jamais vide
        final Llvm.IR ins = Try.toTry( // Conversion du Optional<Try<Llvm.IR>> en Try<Try<Llvm.IR>>
                statements.stream()
                        .map(s -> Try.tryThis(() -> s.toIR(newTable))) // Application de la fonction toIr sur chacune des déclarations
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

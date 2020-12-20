package TP2.asd.function;

import TP2.Context;
import TP2.TypeException;
import TP2.asd.statement.Statement;
import TP2.asd.type.Type;
import TP2.llvm.Llvm;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Function implements IFunction {

    private final List<FunctionParam> params;
    private final Type returnType;
    private final String ident;
    private final Statement body;

    public Function(List<FunctionParam> params, Type returnType, String ident, Statement body) {
        this.params = params;
        this.returnType = returnType;
        this.ident = ident;
        this.body = body;
    }

    @Override
    public String pp() {
        return "FUNC " + returnType.toString() + " " + ident + "(" +
                params.stream().map(FunctionParam::toString).collect(Collectors.joining(", "))
                + ")";
    }

    @Override
    public Llvm.IR toIR(Context table) throws TypeException {
        final Optional<Context.Symbol> symbolOpt = table.lookupSymbol(ident);

        // Création du nouveau symbole
        List<Context.VariableSymbol> symbols = params.stream()
                .map(FunctionParam::toVariableSymbol).collect(Collectors.toList());
        final Context.FunctionSymbol sym = new Context.FunctionSymbol(returnType, ident, symbols, true);

        // Si un symbole du même nom est déjà déclaré
        if(symbolOpt.isPresent()) {
            final Context.Symbol symbol = symbolOpt.get();
            if(!(symbol instanceof Context.FunctionSymbol))
                throw new TypeException("Identifier '" + ident + "' is not an identifier of function.", this::pp);
            Context.FunctionSymbol funSymbol = (Context.FunctionSymbol)symbol;

            if(funSymbol.isDefined())
                throw new IllegalStateException("Function '" + ident + "' is already defined." + "\nat '" + pp() + "'.");

            if(!funSymbol.isDefined() && !funSymbol.isEqualIgnoreDefine(sym))
                throw new IllegalStateException("Function '" + ident + "' does not correspond with '" +
                        ident + "'\nat '" + pp() + "'.");

            table.removeSymbol(ident);
        }

        // Ajout à la table des symboles
        table.addSymbol(sym);

        // Nouvelle table des contextes & ajout des paramètres
        final Context newTable = new Context(table, sym);

        /*final Optional<Try<Llvm.IR>> irParam = params.stream()
                .map(v -> Try.tryThis(() -> (new VariableDeclaration(v.toVariableSymbol().getType(), v.getIdent())).toIR(newTable))) // Délégation à Declaration pour l'ajout à la table des symboles
                .reduce((acc, ir) -> acc.reduce(ir, Llvm.IR::append)); // Append de tous les IR calculés

        // Gestion des erreurs
        if (irParam.isPresent() && irParam.get().failed())
            irParam.get().get();

        // Récupération de tous les VarSymbols associés
        final List<Context.VariableSymbol> varSymbols = params.stream()
                .map(p -> newTable.lookupSymbol(p.getIdent()))
                .filter(Optional::isPresent) // Cas impossible, mais pour éviter un dangerous get
                .map(o -> (Context.VariableSymbol) o.get())
                .collect(Collectors.toList());*/

        // Concaténation des déclarations des paramètres si ils existent
        /*final Llvm.IR irFinal = Try.toTry(irParam).flatMap(i -> i)
                .getOrDefault(new Llvm.IR(Llvm.empty(), Llvm.empty()));


        final Optional<Llvm.IR> irAssign = varSymbols.stream()
                .map(v -> new Llvm.Assignment(v.getType().toLlvmType(), "%" + v.getIdent(), v.toString())) // Création de l'affectation
                .map(i -> new Llvm.IR(Llvm.empty(), new LinkedList<>(Collections.singletonList(i))))
                .reduce(Llvm.IR::append); // Append de tous les IR calculés

        // Concaténation des affectations
        irFinal.append(Try.toTry(irAssign).getOrDefault(new Llvm.IR(Llvm.empty(), Llvm.empty())));

        // Transformation en Variable LLVM
        final List<Llvm.Variable> variables = varSymbols.stream().
                map(symb -> new Llvm.Variable(symb.getType().toLlvmType(), "%" + symb.getIdent()))
                .collect(Collectors.toList());*/

        final Llvm.IR irFinal = params.stream().map(v -> v.toIR(newTable)).reduce(Llvm.IR::append)
                .orElse(new Llvm.IR(Llvm.empty(), Llvm.empty()));

        irFinal.append(body.toIr(newTable));

        // Transformation en Variable LLVM
        final List<Llvm.Variable> variables = params.stream()
                .map(p -> newTable.lookupSymbol(p.getIdent()))
                .filter(Optional::isPresent) // Cas impossible, mais pour éviter un dangerous get
                .map(o -> (Context.VariableSymbol) o.get())
                .map(symb -> new Llvm.Variable(symb.getType().toLlvmType(), "%" + symb.getIdent()))
                .collect(Collectors.toList());

        final Llvm.Instruction ins = new Llvm.Function(ident, variables, returnType.toLlvmType(), irFinal.getCode());


        return new Llvm.IR(irFinal.getHeader(), new LinkedList<>(Collections.singletonList(ins)));
    }

    @Override
    public String getIdent() {
        return ident;
    }
}

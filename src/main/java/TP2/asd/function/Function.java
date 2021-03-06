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

        // Création des instructions
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

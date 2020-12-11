package TP2.asd.reference;

import TP2.Context;
import TP2.TypeException;
import TP2.asd.expression.Expression;
import TP2.llvm.Llvm;

import java.util.Optional;

public class VariableReference extends Reference {

    private final String ident;

    public VariableReference(String ident) {
        this.ident = ident;
    }

    @Override
    public String pp() {
        return ident;
    }

    @Override
    public Expression.RetExpression toIR(Context table) {
        final Optional<Context.Symbol> variable = table.lookupSymbol(ident);

        final Context.VariableSymbol var = isValid(variable);

        return new Expression.RetExpression(new Llvm.IR(Llvm.empty(), Llvm.empty()), var.getType(), var.toString());
    }

    private Context.VariableSymbol isValid(Optional<Context.Symbol> symbol) {
        if(!symbol.isPresent())
            throw new NullPointerException("Variable '" + ident + "' is not initialized." + "\nat '" + pp() + "'.");

        //TODO À tester
        if(!(symbol.get() instanceof Context.VariableSymbol))
            throw new TypeException("Identifier '" + ident + "' is not an identifier of variable.", this::pp);

        return (Context.VariableSymbol)symbol.get();
    }
}

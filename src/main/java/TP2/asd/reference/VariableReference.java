package TP2.asd.reference;

import TP2.SymbolTable;
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
    public Expression.RetExpression toIR(SymbolTable table) {
        final Optional<SymbolTable.Symbol> variable = table.lookup(ident);

        final SymbolTable.VariableSymbol var = isValid(variable);

        return new Expression.RetExpression(new Llvm.IR(Llvm.empty(), Llvm.empty()), var.getType(), var.toString());
    }

    private SymbolTable.VariableSymbol isValid(Optional<SymbolTable.Symbol> symbol) {
        if(!symbol.isPresent())
            throw new NullPointerException("Variable '" + ident + "' is not initialized." + "\nat '" + pp() + "'.");

        //TODO À tester
        if(!(symbol.get() instanceof SymbolTable.VariableSymbol))
            throw new TypeException("Identifier '" + ident + "' is not an identifier of variable.", this::pp);

        return (SymbolTable.VariableSymbol)symbol.get();
    }
}

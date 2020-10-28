package TP2.asd.expression;

import TP2.SymbolTable;
import TP2.TypeException;
import TP2.asd.type.Array;
import TP2.asd.type.Int;
import TP2.llvm.Llvm;

import java.util.Optional;

public class VarExpression extends Expression {

    private final String ident;

    public VarExpression(String ident) {
        this.ident = ident;
    }

    @Override
    public String pp() {
        return null;
    }

    @Override
    public RetExpression toIR(SymbolTable table) throws TypeException {

        final Optional<SymbolTable.Symbol> optSymbol = table.lookup(ident);

        // Variable non initialisé
        if(!optSymbol.isPresent())
            throw new NullPointerException("Variable '" + ident + "' is not initialized." + "\nat '" + pp() + "'.");

        // Identifier de fonctions
        if(!(optSymbol.get() instanceof SymbolTable.VariableSymbol))
            throw new TypeException("Identifier '" + ident + "' is not an identifier of variable.", this::pp);

        final SymbolTable.VariableSymbol var = (SymbolTable.VariableSymbol) optSymbol.get();

        return new RetExpression(new Llvm.IR(Llvm.empty(), Llvm.empty()), var.getType().toLlvmType(), "%" + var.toString());
    }
}

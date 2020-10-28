package TP2.asd.expression;

import TP2.SymbolTable;
import TP2.TypeException;
import TP2.asd.type.Array;
import TP2.asd.type.Type;
import TP2.llvm.Llvm;

import java.util.Optional;

public class ArrayExpression extends Expression {

    private final String ident;
    private final int index;

    public ArrayExpression(String ident, int index) {
        this.ident = ident;
        this.index = index;
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

        // Vérification du type
        if(!(var.getType() instanceof Array))
            throw new TypeException("Type mismatch: 'ARRAY' expected, found '" + var.getType().pp(), this::pp);

        Array type = ((Array)var.getType());

        if(type.getSize() <= index)
            throw new ArrayIndexOutOfBoundsException("Index " + index + " found for an array of size " + type.getSize() + ".");


        //TODO À finir
        return new RetExpression(null, type.getType().toLlvmType(), null);
    }
}

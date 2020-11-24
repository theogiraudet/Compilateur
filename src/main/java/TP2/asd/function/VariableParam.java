package TP2.asd.function;

import TP2.SymbolTable;
import TP2.asd.type.Type;
import TP2.llvm.Llvm;

public class VariableParam {

    private final Type type;
    private final String ident;

    public VariableParam(Type type, String ident) {
        this.type = type;
        this.ident = ident;
    }

    public SymbolTable.VariableSymbol toVariableSymbol() {
        return new SymbolTable.VariableSymbol(type, ident);
    }

    public Llvm.Variable toLlvm() {
        return new Llvm.Variable(type.toLlvmType(), ident);
    }
}

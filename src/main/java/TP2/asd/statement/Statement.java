package TP2.asd.statement;

import TP2.SymbolTable;
import TP2.TypeException;
import TP2.llvm.Llvm;

public abstract class Statement {
    public abstract String pp();
    public abstract Llvm.IR toIR(SymbolTable table) throws TypeException, NullPointerException;
}

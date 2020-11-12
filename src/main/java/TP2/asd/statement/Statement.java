package TP2.asd.statement;

import TP2.SymbolTable;
import TP2.TypeException;
import TP2.llvm.Llvm;

public interface Statement {
    public String pp();
    public Llvm.IR toIR(SymbolTable table) throws TypeException, NullPointerException;
}

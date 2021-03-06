package TP2.asd.statement;

import TP2.Context;
import TP2.TypeException;
import TP2.llvm.Llvm;

public interface Statement {
    String pp(int nbIndent);
    Llvm.IR toIr(Context table) throws TypeException, NullPointerException;
}

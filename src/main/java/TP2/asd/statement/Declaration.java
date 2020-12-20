package TP2.asd.statement;

import TP2.Context;
import TP2.TypeException;
import TP2.llvm.Llvm;

public interface Declaration {

    String pp(int nbIndent);

    Llvm.IR toIR(Context table) throws TypeException, NullPointerException;

}

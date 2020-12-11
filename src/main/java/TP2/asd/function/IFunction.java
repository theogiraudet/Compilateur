package TP2.asd.function;

import TP2.Context;
import TP2.TypeException;
import TP2.llvm.Llvm;

public interface IFunction {
    String pp();
    Llvm.IR toIR(Context table) throws TypeException;
    String getIdent();
}

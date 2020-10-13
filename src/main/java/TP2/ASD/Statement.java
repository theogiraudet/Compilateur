package TP2.ASD;

import TP2.llvm.Llvm;

public abstract class Statement {
    public abstract String pp();
    public abstract Llvm.IR toIR();
}

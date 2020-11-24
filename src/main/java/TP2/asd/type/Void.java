package TP2.asd.type;

import TP2.llvm.Llvm;

public class Void extends Type {

    @Override
    public String pp() {
        return "VOID";
    }

    @Override
    public Llvm.Type toLlvmType() {
        return new Llvm.Void();
    }
}

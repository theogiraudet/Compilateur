package TP2.asd.type;

import TP2.llvm.Llvm;

public class Array extends Type {

    private final Type type;
    private final int size;

    public Array(Type type, int size) {
        this.type = type;
        this.size = size;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String pp() {
        return type.pp() + '[' + size + ']';
    }

    @Override
    public Llvm.Type toLlvmType() {
        return new Llvm.Array(type.toLlvmType(), size);
    }
}

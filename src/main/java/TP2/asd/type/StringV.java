package TP2.asd.type;

import TP2.llvm.Llvm;

public class StringV extends Type implements Printable {

    private final int size;

    public StringV(int size) {
        this.size = size;
    }

    @Override
    public String pp() {
        return null;
    }

    @Override
    public Llvm.Type toLlvmType() {
        return new Llvm.StringL(size);
    }

    @Override
    public String toPrintable() {
        return "%s";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof StringV && ((StringV) obj).size == size;
    }
}

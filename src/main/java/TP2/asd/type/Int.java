package TP2.asd.type;

import TP2.llvm.Llvm;

public class Int extends Type implements Printable, Readable {
    public String pp() {
        return "INT";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Int;
    }

    public Llvm.Type toLlvmType() {
        return new Llvm.Int();
    }

    @Override
    public String toPrintable() {
        return "%d";
    }

    @Override
    public String toReadable() {
        return toPrintable();
    }
}
package TP2.ASD;

import TP2.Llvm;

public class Int extends Type {
    public String pp() {
      return "INT";
    }

    @Override public boolean equals(Object obj) {
      return obj instanceof Int;
    }

    public Llvm.Type toLlvmType() {
      return new Llvm.Int();
    }
  }
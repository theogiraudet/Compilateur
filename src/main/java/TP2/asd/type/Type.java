package TP2.asd.type;

import TP2.llvm.Llvm;

// Warning: this is the type from VSL+, not the LLVM types!
  public abstract class Type {
    public abstract String pp();
    public abstract Llvm.Type toLlvmType();
  }
package TP2.asd.expression;

import TP2.llvm.Llvm;

// Concrete class for Expression: constant (integer) case
  public class IntegerExpression extends Expression {
    int value;
    public IntegerExpression(int value) {
      this.value = value;
    }

    public String pp() {
      return "" + value;
    }

    public RetExpression toIR() {
      // Here we simply return an empty IR
      // the `result' of this expression is the integer itself (as string)
      return new RetExpression(new Llvm.IR(Llvm.empty(), Llvm.empty()), new Llvm.Int(), "" + value);
    }
  }
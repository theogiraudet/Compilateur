package TP2.asd.expression;

import TP2.SymbolTable;
import TP2.asd.type.Int;
import TP2.llvm.Llvm;

// Concrete class for Expression: constant (integer) case
  public class IntegerExpression implements Expression {

    private final int value;

    public IntegerExpression(int value) {
      this.value = value;
    }

    public String pp() {
      return "" + value;
    }

    public RetExpression toIR(SymbolTable table) {
      // Here we simply return an empty IR
      // the `result' of this expression is the integer itself (as string)
      return new RetExpression(new Llvm.IR(Llvm.empty(), Llvm.empty()), new Int(), "" + value);
    }
  }
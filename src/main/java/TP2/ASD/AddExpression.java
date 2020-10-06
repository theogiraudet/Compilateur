package TP2.ASD;

import TP2.Llvm;
import TP2.TypeException;
import TP2.Utils;
import TP2.utils.QuadriFunction;

// Concrete class for Expression: add case
  public class AddExpression extends BinaryOperation {

  public AddExpression(Expression left, Expression right) {
    super(left, right);
  }

  @Override
  protected QuadriFunction<Llvm.Type, String, String, String, Llvm.Instruction> getFunction() {
    return (a, b, c, d) -> new Llvm.Add(a, b, c, d);
  }

  // Pretty-printer
    public String pp() {
      return "(" + left.pp() + " + " + right.pp() + ")";
    }


  }
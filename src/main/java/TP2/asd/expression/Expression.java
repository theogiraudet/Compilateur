package TP2.asd.expression;

import TP2.Context;
import TP2.TypeException;
import TP2.asd.type.Type;
import TP2.llvm.Llvm;

public interface Expression {
    public String pp();
    public Expression.RetExpression toIR(Context table) throws TypeException;

    // Object returned by toIR on expressions, with IR + synthesized attributes
    static public class RetExpression {
      // The LLVM IR:
      public Llvm.IR ir;
      // And additional stuff:
      public Type type; // The type of the expression
      public String result; // The name containing the expression's result
      // (either an identifier, or an immediate value)

      public RetExpression(Llvm.IR ir, Type type, String result) {
        this.ir = ir;
        this.type = type;
        this.result = result;
      }
    }
  }
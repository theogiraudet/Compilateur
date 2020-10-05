package TP2.ASD;

import TP2.Llvm;
import TP2.TypeException;

public class Program {
    Expression e; // What a program contains. TODO : change when you extend the language

    public Program(Expression e) {
      this.e = e;
    }

    // Pretty-printer
    public String pp() {
      return e.pp();
    }

    // IR generation
    public Llvm.IR toIR() throws TypeException {
      // TODO : change when you extend the language

      // computes the IR of the expression
      Expression.RetExpression retExpr = e.toIR();
      // add a return instruction
      Llvm.Instruction ret = new Llvm.Return(retExpr.type.toLlvmType(), retExpr.result);
      retExpr.ir.appendCode(ret);

      return retExpr.ir;
    }
  }
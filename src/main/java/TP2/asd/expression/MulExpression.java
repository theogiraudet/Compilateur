package TP2.asd.expression;

import TP2.llvm.BinOpInstruction;
import TP2.llvm.Llvm;
import TP2.utils.QuadriFunction;

public class MulExpression extends BinaryOperation {

    public MulExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    protected QuadriFunction<Llvm.Type, String, String, String, Llvm.Instruction> getFunction() {
        return BinOpInstruction::mul;
    }

    // Pretty-printer
    public String pp() {
        return "(" + left.pp() + " * " + right.pp() + ")";
    }
}

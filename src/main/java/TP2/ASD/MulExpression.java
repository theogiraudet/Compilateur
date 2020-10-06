package TP2.ASD;

import TP2.Llvm;
import TP2.utils.QuadriFunction;

public class MulExpression extends BinaryOperation {

    public MulExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    protected QuadriFunction<Llvm.Type, String, String, String, Llvm.Instruction> getFunction() {
        return (a, b, c, d) -> new Llvm.Mul(a, b, c, d);
    }

    // Pretty-printer
    public String pp() {
        return "(" + left.pp() + " * " + right.pp() + ")";
    }
}

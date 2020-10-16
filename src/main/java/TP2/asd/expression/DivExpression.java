package TP2.asd.expression;

import TP2.llvm.BinOpInstruction;
import TP2.llvm.Llvm;
import TP2.utils.QuadriFunction;

public class DivExpression extends BinaryOperation {

    public DivExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    protected QuadriFunction<Llvm.Type, String, String, String, Llvm.Instruction> getFunction() {
        return (a, b, c, d) -> new BinOpInstruction.Div(a, b, c, d);
    }

    // Pretty-printer
    public String pp() {
        return "(" + left.pp() + " / " + right.pp() + ")";
    }

}

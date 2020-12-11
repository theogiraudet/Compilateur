package TP2.asd.expression;

import TP2.Context;
import TP2.TypeException;
import TP2.llvm.Llvm;

public class Void implements Expression {
    @Override
    public String pp() {
        return "";
    }

    @Override
    public Expression.RetExpression toIR(Context table) throws TypeException {
        return new RetExpression(new Llvm.IR(Llvm.empty(), Llvm.empty()), new TP2.asd.type.Void(), null);
    }
}

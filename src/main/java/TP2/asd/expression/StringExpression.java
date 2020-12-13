package TP2.asd.expression;

import TP2.Context;
import TP2.TypeException;
import TP2.asd.type.StringV;
import TP2.llvm.Llvm;

public class StringExpression implements Expression {

    private final String str;

    public StringExpression(String str) {
        this.str = str;
    }

    @Override
    public String pp() {
        return "\"str\"";
    }

    @Override
    public RetExpression toIR(Context table) throws TypeException {
        return new RetExpression(new Llvm.IR(Llvm.empty(), Llvm.empty()), new StringV(str.length()), "" + str);
    }
}

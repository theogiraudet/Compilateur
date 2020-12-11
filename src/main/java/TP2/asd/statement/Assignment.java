package TP2.asd.statement;

import TP2.Context;
import TP2.TypeException;
import TP2.asd.expression.Expression;
import TP2.asd.reference.Reference;
import TP2.llvm.Llvm;
import TP2.utils.Utils;

public class Assignment implements Statement {

    private final Reference variable;
    private final Expression expression;

    public Assignment(Reference var, Expression e) {
        this.variable = var;
        this.expression = e;
    }

    public String pp(int indent) {
        return Utils.indent(indent) + variable.pp() + " := " + expression.pp();
    }

    @Override
    public Llvm.IR toIr(Context table) throws TypeException, NullPointerException {

        final Expression.RetExpression varRet = variable.toIR(table);
        final Expression.RetExpression exp = expression.toIR(table);

        if(!exp.type.equals(varRet.type))
            throw new TypeException("Type mismatch: '" + varRet.type + "' expected, found '" + exp.type  + '\'', () -> this.pp(0));

        final Llvm.Instruction instruction = new Llvm.Assignment(exp.type.toLlvmType(), exp.result, varRet.result);

        return varRet.ir.append(exp.ir.appendCode(instruction));
    }
}


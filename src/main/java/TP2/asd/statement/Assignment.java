package TP2.asd.statement;

import TP2.asd.expression.Expression;
import TP2.SymbolTable;
import TP2.TypeException;
import TP2.asd.variable.Variable;
import TP2.llvm.Llvm;
import TP2.SymbolTable.*;

import java.util.Optional;

public class Assignment extends Statement {

    private final Variable variable;
    private final Expression expression;

    public Assignment(Variable var, Expression e) {
        this.variable = var;
        this.expression = e;
    }

    public String pp() {
        return variable.pp() + " := " + expression.pp();
    }

    @Override
    public Llvm.IR toIR(SymbolTable table) throws TypeException, NullPointerException {

        final Expression.RetExpression exp = expression.toIR(table);
        final Expression.RetExpression varRet = variable.toIR(table);

        if(!exp.type.equals(varRet.type))
            throw new TypeException("Type mismatch: '" + varRet.type + "' expected, found '" + exp.type, this::pp);

        final Llvm.Instruction instruction = new Llvm.Assignment(exp.type, exp.result, varRet.result);

        return varRet.ir.append(exp.ir.appendCode(instruction));
    }
}


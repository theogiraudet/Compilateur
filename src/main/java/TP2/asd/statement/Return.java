package TP2.asd.statement;

import TP2.Context;
import TP2.TypeException;
import TP2.asd.expression.Expression;
import TP2.asd.expression.Void;
import TP2.asd.type.Type;
import TP2.llvm.Llvm;

import java.util.Optional;

public class Return implements Statement {

    private final Expression expr;

    public Return(Expression expr) {
        this.expr = expr;
    }

    public Return() { this(new Void()); }

    @Override
    public String pp(int nbIndent) {
        return "RETURN " + expr.pp();
    }

    @Override
    public Llvm.IR toIr(Context table) throws TypeException, NullPointerException {
        final Expression.RetExpression retExpr = expr.toIR(table);

        final Optional<Type> typeOpt = table.getAssociedFunction().map(Context.FunctionSymbol::getReturnType);
        if(!typeOpt.isPresent())
            throw new IllegalStateException("Not a function context." + "\nat '" + pp(0) + "'.");

        final Type type = typeOpt.get();
        if(!type.equals(retExpr.type))
            throw new TypeException("Return type mismatch: '" + type.pp() + "' expected, found '" +
                    retExpr.type.pp() + "'." , () -> this.pp(0));


        final Llvm.Instruction ins = new Llvm.Return(type.toLlvmType(), retExpr.result);

        return retExpr.ir.appendCode(ins);
    }
}

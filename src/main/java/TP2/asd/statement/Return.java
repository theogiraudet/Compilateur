package TP2.asd.statement;

import TP2.SymbolTable;
import TP2.TypeException;
import TP2.asd.expression.Expression;
import TP2.llvm.Llvm;

public class Return implements Statement {

    private final Expression expr;

    public Return(Expression expr) {
        this.expr = expr;
    }

    @Override
    public String pp(int nbIndent) {
        return null;
    }

    @Override
    public Llvm.IR toIr(SymbolTable table) throws TypeException, NullPointerException {
        return null;
    }
}

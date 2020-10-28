package TP2.asd.statement;

import TP2.asd.expression.Expression;
import TP2.SymbolTable;
import TP2.TypeException;
import TP2.llvm.Llvm;
import TP2.SymbolTable.*;

import java.util.Optional;

public class Assignment extends Statement {

    private final String ident;
    private final Expression expression;

    public Assignment(String i, Expression e) {
        this.ident = i;
        this.expression = e;
    }

    public String pp() {
        return ident + " := " + expression.pp();
    }

    @Override
    public Llvm.IR toIR(SymbolTable table) throws TypeException, NullPointerException {

        final Expression.RetExpression exp = expression.toIR(table);
        final Optional<Symbol> variable = table.lookup(ident);

        if(!variable.isPresent())
            throw new NullPointerException("Variable '" + ident + "' is not initialized." + "\nat '" + pp() + "'.");

        if(!(variable.get() instanceof VariableSymbol))
            throw new TypeException("Identifier '" + ident + "' is not an identifier of variable.", this::pp);

        final VariableSymbol var = (VariableSymbol) variable.get();
        if(!exp.type.equals(var.getType().toLlvmType()))
            throw new TypeException("Type mismatch: '" + var.getType().pp() + "' expected, found '" + exp.type, this::pp);

        final Llvm.Instruction instruction = new Llvm.Assignment(exp.type, exp.result, var.toString());

        exp.ir.appendCode(instruction);
        return exp.ir;
    }
}


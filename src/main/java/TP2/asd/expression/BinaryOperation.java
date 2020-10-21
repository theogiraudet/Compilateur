package TP2.asd.expression;

import TP2.llvm.Llvm;
import TP2.TypeException;
import TP2.Utils;
import TP2.utils.QuadriFunction;

/**
 * Représente une opération binaire
 */
public abstract class BinaryOperation extends Expression {

    protected Expression left;
    protected Expression right;

    public BinaryOperation(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     *
     * @return l'instruction correspondant à l'opération courante
     */
    protected abstract QuadriFunction<Llvm.Type, String, String, String, Llvm.Instruction> getFunction();

    @Override
    public RetExpression toIR() throws TypeException {
        final RetExpression leftRet = left.toIR();
        final RetExpression rightRet = right.toIR();

        // We check if the types mismatches
        if(!leftRet.type.equals(rightRet.type)) {
            throw new TypeException("Type mismatch: have '" + leftRet.type + "' and '" + rightRet.type + "'.", this::pp);
        }

        // We base our build on the left generated IR:
        // append right code
        leftRet.ir.append(rightRet.ir);

        // allocate a new identifier for the result
        String result = Utils.newtmp();

        // new add instruction result = left op right
        Llvm.Instruction sub = getFunction().apply(leftRet.type, leftRet.result, rightRet.result, result);

        // append this instruction
        leftRet.ir.appendCode(sub);

        // return the generated IR, plus the type of this expression
        // and where to find its result
        return new RetExpression(leftRet.ir, leftRet.type, result);
    }
}
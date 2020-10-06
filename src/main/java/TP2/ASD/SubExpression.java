package TP2.ASD;

import TP2.Llvm;
import TP2.TypeException;
import TP2.Utils;

public class SubExpression extends Expression {

    private Expression left;
    private Expression right;

    public SubExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String pp() {
        return "(" + left.pp() + " + " + right.pp() + ")";
    }

    @Override
    public RetExpression toIR() throws TypeException {
        RetExpression leftRet = left.toIR();
        RetExpression rightRet = right.toIR();

        // We check if the types mismatches
        if(!leftRet.type.equals(rightRet.type)) {
            throw new TypeException("type mismatch: have " + leftRet.type + " and " + rightRet.type);
        }

        // We base our build on the left generated IR:
        // append right code
        leftRet.ir.append(rightRet.ir);

        // allocate a new identifier for the result
        String result = Utils.newtmp();

        // new add instruction result = left + right
        Llvm.Instruction sub = new Llvm.Add(leftRet.type.toLlvmType(), leftRet.result, rightRet.result, result);

        // append this instruction
        leftRet.ir.appendCode(sub);

        // return the generated IR, plus the type of this expression
        // and where to find its result
        return new RetExpression(leftRet.ir, leftRet.type, result);
    }
}

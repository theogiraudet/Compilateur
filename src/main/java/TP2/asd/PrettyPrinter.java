package TP2.asd;

import TP2.asd.core.Visitor;
import TP2.asd.expression.*;
import TP2.asd.reference.ArrayElementReference;
import TP2.asd.reference.VariableReference;
import TP2.asd.statement.*;

public class PrettyPrinter implements Visitor {

    @Override
    public void visitAddExpression(AddExpression expr) {
            }

    @Override
    public void visitSubExpression(SubExpression expr) {

    }

    @Override
    public void visitDivExpression(DivExpression expr) {

    }

    @Override
    public void visitMulExpression(MulExpression expr) {

    }

    @Override
    public void visitIntegerExpression(IntegerExpression expr) {

    }

    @Override
    public void visitDereferenceExpression(IntegerExpression expr) {

    }

    @Override
    public void visitAssignment(Assignment stat) {

    }

    @Override
    public void visitBlock(Block stat) {

    }

    @Override
    public void visitIf(If stat) {

    }

    @Override
    public void visitWhile(While stat) {

    }

    @Override
    public void visitProgram(Program stat) {

    }

    @Override
    public void visitDeclaration(Declaration decl) {

    }

    @Override
    public void visitArrayElementReference(ArrayElementReference stat) {

    }

    @Override
    public void visitVariableReference(VariableReference stat) {

    }
}

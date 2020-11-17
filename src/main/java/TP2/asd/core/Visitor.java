package TP2.asd.core;

import TP2.asd.Program;
import TP2.asd.expression.*;
import TP2.asd.reference.ArrayElementReference;
import TP2.asd.reference.VariableReference;
import TP2.asd.statement.*;

public interface Visitor {

    void visitAddExpression(AddExpression expr);
    void visitSubExpression(SubExpression expr);
    void visitDivExpression(DivExpression expr);
    void visitMulExpression(MulExpression expr);
    void visitIntegerExpression(IntegerExpression expr);
    void visitDereferenceExpression(IntegerExpression expr);

    void visitAssignment(Assignment stat);
    void visitBlock(Block stat);
    void visitIf(If stat);
    void visitWhile(While stat);

    void visitProgram(Program stat);

    void visitDeclaration(Declaration decl);

    void visitArrayElementReference(ArrayElementReference stat);
    void visitVariableReference(VariableReference stat);

}


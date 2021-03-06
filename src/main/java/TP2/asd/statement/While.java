package TP2.asd.statement;

import TP2.Context;
import TP2.TypeException;
import TP2.asd.expression.Expression;
import TP2.asd.type.Int;
import TP2.llvm.Llvm;
import TP2.llvm.Llvm.*;
import TP2.utils.Utils;

import java.util.Collections;
import java.util.LinkedList;

public class While implements Statement {

    private final Expression condition;
    private final Statement whileStatement;

    public While(Expression condition, Statement whileStatement) {
        this.condition = condition;
        this.whileStatement = whileStatement;
    }

    @Override
    public String pp(int nbIndent) {
        return Utils.indent(nbIndent) + "WHILE " + condition.pp() + " DO\n" + whileStatement.pp(nbIndent + 1)
                + Utils.indent(nbIndent) + "DONE\n";
    }

    @Override
    public Llvm.IR toIr(Context table) throws TypeException, NullPointerException {
        final Expression.RetExpression condRet = condition.toIR(table);


        if(!(condRet.type instanceof Int))
            throw new TypeException("Type mismatch: 'INT' expected, found '" + condRet.type, () -> this.pp(0));

        final Llvm.IR ir = condRet.ir;
        // Création des labels
        final String condLabel = Utils.newtmp();

        final String number = Utils.newlab("");
        final String whileLabel = "While" + number;
        final String doLabel = "Do" + number;
        final String doneLabel = "Done" + number;

        // Ajout du label du while en header de l'évaluation de la condition
        final Llvm.IR irReturn = new IR(new LinkedList<>(), new LinkedList<>(Collections.singletonList(new Label(whileLabel))));
        irReturn.append(ir);

        // Ajout du test d'égalité entre condRet.result et 0
        irReturn.appendCode(new Conditional(condRet.result, 0, condLabel, Conditional.Comparator.NOT_EQUAL));

        // Ajout du branchement
        irReturn.appendCode(new ConditionalBranch(condLabel, doLabel, doneLabel));

        // Do
        irReturn.appendCode(new Llvm.Label(doLabel));
        final Llvm.IR whileIr = whileStatement.toIr(table);
        irReturn.append(whileIr);
        // Retour à l'évaluation de la condition
        irReturn.appendCode(new UnconditionalBranch(whileLabel));

        irReturn.appendCode(new Label(doneLabel));

        // End
        return irReturn;
    }
}

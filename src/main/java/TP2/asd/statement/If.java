package TP2.asd.statement;

import TP2.Context;
import TP2.TypeException;
import TP2.asd.expression.Expression;
import TP2.asd.type.Int;
import TP2.llvm.Llvm;
import TP2.llvm.Llvm.Conditional;
import TP2.llvm.Llvm.ConditionalBranch;
import TP2.llvm.Llvm.Label;
import TP2.llvm.Llvm.UnconditionalBranch;
import TP2.utils.Utils;

public class If implements Statement {

    private final Expression condition;
    private final Statement elseStatement;
    private final Statement ifStatement;

    public If(Expression cond, Statement ifStatement) {
        this(cond, ifStatement, null);
    }

    public If(Expression cond, Statement ifStatement, Statement elseStatement) {
        this.condition = cond;
        this.ifStatement = ifStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public String pp(int nbIndent) {
        String result = Utils.indent(nbIndent) + "IF " + condition.pp() + " THEN\n" + ifStatement.pp(nbIndent + 1);
        if(elseStatement == null)
            return result + "\n" + Utils.indent(nbIndent) + "FI";
        return result + "\n" + Utils.indent(nbIndent) + "ELSE\n" + elseStatement.pp(nbIndent + 1) + "\n" + Utils.indent(nbIndent) + "FI";
    }

    @Override
    public Llvm.IR toIr(Context table) throws TypeException, NullPointerException {
        final Expression.RetExpression condRet = condition.toIR(table);

        if(!(condRet.type instanceof Int))
            throw new TypeException("Type mismatch: 'INT' expected, found '" + condRet.type + "'.", () -> this.pp(0));

        final Llvm.IR ir = condRet.ir;
        // Création des labels
        final String condLabel = Utils.newtmp();

        final String number = Utils.newlab("");
        final String ifLabel = "If" + number;
        final String elseLabel = "Else" + number;
        final String endLabel = "End" + number;

        // Ajout du test d'égalité entre condRet.result et 0
        ir.appendCode(new Conditional(condRet.result, 0, condLabel, Conditional.Comparator.NOT_EQUAL));

        // Ajout du branchement
        ir.appendCode(new ConditionalBranch(condLabel, ifLabel, elseStatement != null ? elseLabel : endLabel));

        // If
        final Llvm.IR ifIr = ifStatement.toIr(table);
        ir.appendCode(new Label(ifLabel));
        ir.append(ifIr);
        if(elseStatement != null)
            ir.appendCode(new UnconditionalBranch(endLabel));

        // Else
        if(elseStatement != null) {
            final Llvm.IR elseIr = elseStatement.toIr(table);

            ir.appendCode(new Label(elseLabel));
            ir.append(elseIr);
        }

        // End
        ir.appendCode(new Label(endLabel));
        return ir;
    }
}

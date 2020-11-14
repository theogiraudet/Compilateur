package TP2.asd.statement;

import TP2.SymbolTable;
import TP2.TypeException;
import TP2.Utils;
import TP2.asd.expression.Expression;
import TP2.llvm.Llvm;
import TP2.llvm.Llvm.*;

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
    public String pp() {
        String result = "IF " + condition.pp() + " THEN\n" + ifStatement.pp();
        if(elseStatement==null){
            return result;
        }
        return result+=  "\nELSE\n" + elseStatement.pp();
    }

    @Override
    public Llvm.IR toIR(SymbolTable table) throws TypeException, NullPointerException {
        final Expression.RetExpression condRet = condition.toIR(table);
        final Llvm.IR ifIr = ifStatement.toIR(table);

        if(!(condRet.type instanceof Llvm.Int))
            throw new TypeException("Type mismatch: 'INT' expected, found '" + condRet.type, this::pp);

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
        ir.appendCode(new Label(ifLabel));
        ir.append(ifIr);
        if(elseStatement != null)
            ir.appendCode(new UnconditionalBranch(endLabel));

        // Else
        if(elseStatement != null) {
            final Llvm.IR elseIr = elseStatement.toIR(table);

            ir.appendCode(new Label(elseLabel));
            ir.append(elseIr);
        }

        // End
        ir.appendCode(new Label(endLabel));
        return ir;
    }
}

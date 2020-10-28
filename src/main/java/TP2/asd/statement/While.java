package TP2.asd.statement;

import TP2.SymbolTable;
import TP2.TypeException;
import TP2.Utils;
import TP2.asd.expression.Expression;
import TP2.llvm.Llvm;
import TP2.llvm.Llvm.*;

public class While extends Statement {

    private final Expression condition;
    private final Statement whileStatement;

    public While(Expression condition, Statement whileStatement) {
        this.condition = condition;
        this.whileStatement = whileStatement;
    }

    @Override
    public String pp() {
        return null;
    }

    @Override
    public Llvm.IR toIR(SymbolTable table) throws TypeException, NullPointerException {
        final Expression.RetExpression condRet = condition.toIR(table);
        final Llvm.IR whileIr = whileStatement.toIR(table);

        if(!(condRet.type instanceof Llvm.Int))
            throw new TypeException("Type mismatch: 'INT' expected, found '" + condRet.type, this::pp);

        final Llvm.IR ir = condRet.ir;
        // Création des labels
        final String condLabel = Utils.newtmp();

        final String number = Utils.newlab("");
        final String whileLabel = "While" + number;
        final String doLabel = "Do" + number;
        final String doneLabel = "Done" + number;


        // Ajout du label du while en header de l'évaluation de la condition
        ir.appendHeader(new Label(whileLabel));

        // Ajout du test d'égalité entre condRet.result et 0
        ir.appendCode(new Conditional(condRet.result, 0, condLabel, Conditional.Comparator.NOT_EQUAL));

        // Ajout du branchement
        ir.appendCode(new ConditionalBranch(condLabel, doLabel, doneLabel));

        // Do
        ir.appendCode(new Llvm.Label(doLabel));
        ir.append(whileIr);
        // Retour à l'évaluation de la condition
        ir.appendCode(new UnconditionalBranch(whileLabel));

        ir.appendCode(new Label(doneLabel));

        // End
        return ir;
    }
}
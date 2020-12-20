package TP2.asd.statement;

import TP2.Context;
import TP2.IllegalFormatException;
import TP2.TypeException;
import TP2.asd.expression.Constant;
import TP2.asd.expression.Expression;
import TP2.asd.type.Printable;
import TP2.llvm.Llvm;
import TP2.utils.Utils;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Print implements Statement {

    private final List<Expression> printables;

    public Print(List<Expression> printables) {
        this.printables = printables;
    }

    @Override
    public String pp(int nbIndent) {
        return "PRINT " + printables.stream().map(Expression::pp).collect(Collectors.joining(", "));
    }

    @Override
    public Llvm.IR toIr(Context table) throws TypeException, NullPointerException {

        final Llvm.IR ir = new Llvm.IR(Llvm.empty(), Llvm.empty());
        final StringBuilder str = new StringBuilder();
        final List<Llvm.Variable> results = new LinkedList<>();

        for (Expression p : printables) {
            final Expression.RetExpression irTmp = p.toIR(table);
            // Si le type n'est pas affichable, on déclenche une erreur
            if (!(irTmp.type instanceof Printable))
                throw new TypeException("Type '" + irTmp.type.pp() + "' is not a printable type.", () -> pp(0));
            if (!(p instanceof Constant)) {
                str.append(((Printable) irTmp.type).toPrintable());
                results.add(new Llvm.Variable(irTmp.type.toLlvmType(), irTmp.result));
            } else
                str.append(irTmp.result);
            ir.append(irTmp.ir);
        }


        final String label = Utils.newglob(".fmt");
        Llvm.Instruction ins;
        final Utils.LLVMStringConstant constant = Utils.stringTransform(str.toString());

        /* Formatage du String et création de la constante globale à placer en tête. Si la création renvoie une erreur
           (caractère non affichable différent de '\n'), alors on complète l'erreur et on la remonte
        */
        try {
            ins = new Llvm.DefineString(label, constant.str, constant.length);
        } catch (IllegalFormatException e) {
            throw new IllegalFormatException(e.getMessage(), () -> pp(0));
        }

        // Ajout du nouveau string en header
        ir.appendHeader(ins);

        final Llvm.Instruction insPrint = new Llvm.Print(new Llvm.StringL(constant.length), label, results);

        return ir.appendCode(insPrint);
    }
}

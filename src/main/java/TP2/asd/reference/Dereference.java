package TP2.asd.reference;

import TP2.SymbolTable;
import TP2.Utils;
import TP2.asd.expression.Expression;
import TP2.llvm.Llvm;

public class Dereference implements Expression {

    private final Reference reference;

    public Dereference(Reference reference) {
        this.reference = reference;
    }

    public String pp() { return null; }

    /**
     * @param table une table de symboles
     * @return l'instruction correspondant à la variable.<br/>
     */
    public Expression.RetExpression toIR(SymbolTable table) {
        // On délègue l'obtention du pointeur vers la valeur à Reference
        final Expression.RetExpression ret = reference.toIR(table);
        final String dest = Utils.newtmp();
        // On déréférence de pointeur obtenu
        final Llvm.Instruction instruction = new Llvm.Load(ret.type, dest, ret.result);
        ret.ir.appendCode(instruction);
        return new Expression.RetExpression(ret.ir, ret.type, dest);
    }
}

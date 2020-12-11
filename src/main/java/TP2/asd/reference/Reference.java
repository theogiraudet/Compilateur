package TP2.asd.reference;

import TP2.Context;
import TP2.asd.expression.Expression;

public abstract class Reference {

    public abstract String pp();

    /**
     * @param table une table de symboles
     * @return l'instruction correspondant à la variable.<br/>
     */
    public abstract Expression.RetExpression toIR(Context table);
}

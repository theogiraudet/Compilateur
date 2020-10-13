package TP2.ASD;
import TP2.llvm.Llvm;

public class Affectation extends Statement {

    public String ident;
    public Expression expression;

    public Affectation(String i, Expression e) {
        this.ident = i;
        this.expression = e;
    }

    public String pp() {
        return ident+" := "+ expression.pp();
    }

    public Llvm.IR toIR(){
        //TODO Faire le toIR()
        return null;
    }
}


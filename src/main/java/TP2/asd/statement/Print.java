package TP2.asd.statement;

import TP2.asd.expression.Expression;
import TP2.llvm.Llvm;

public class Print extends Llvm.Instruction {



    @Override
    public String toString() {
        return null;
    }


    public interface PrintItem {

        void pp();

    }


    /*public class StringItem implements PrintItem {

        private final String str;

        public StringItem(String str) {
            this.str = str;
        }
    }

    public class ExpressionItem implements PrintItem {

        private final String expr;


        public ExpressionItem(String expr) {
            this.expr = expr;
        }
    }*/


}

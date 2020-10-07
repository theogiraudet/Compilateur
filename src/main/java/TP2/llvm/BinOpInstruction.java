package TP2.llvm;

public abstract class BinOpInstruction extends Llvm.Instruction {

    protected Llvm.Type type;
    protected String left;
    protected String right;
    protected String lvalue;

    public BinOpInstruction(Llvm.Type type, String left, String right, String lvalue) {
        this.type = type;
        this.left = left;
        this.right = right;
        this.lvalue = lvalue;
    }

    static public class Add extends BinOpInstruction {
        public Add(Llvm.Type type, String left, String right, String lvalue) {
            super(type, left, right, lvalue);
        }

        public String toString() {
            return lvalue + " = add " + type + " " + left + ", " + right +  "\n";
        }
    }

    static public class Sub extends BinOpInstruction {
        public Sub(Llvm.Type type, String left, String right, String lvalue) {
            super(type, left, right, lvalue);
        }

        public String toString() {
            return lvalue + " = sub " + type + " " + left + ", " + right +  "\n";
        }
    }

    static public class Mul extends BinOpInstruction {
        public Mul(Llvm.Type type, String left, String right, String lvalue) {
            super(type, left, right, lvalue);
        }

        public String toString() {
            return lvalue + " = mul " + type + " " + left + ", " + right +  "\n";
        }
    }

    static public class Div extends BinOpInstruction {
        public Div(Llvm.Type type, String left, String right, String lvalue) {
            super(type, left, right, lvalue);
        }

        public String toString() {
            return lvalue + " = sdiv " + type + " " + left + ", " + right +  "\n";
        }
    }



}

package TP2.llvm;

public final class BinOpInstruction extends Llvm.Instruction {

    protected final String op;
    protected final Llvm.Type type;
    protected final String left;
    protected final String right;
    protected final String lvalue;

    private BinOpInstruction(String op, Llvm.Type type, String left, String right, String lvalue) {
        this.op = op;
        this.type = type;
        this.left = left;
        this.right = right;
        this.lvalue = lvalue;
    }

    public String toString() {
        return lvalue + " = " + op + " " + type + " " + left + ", " + right + "\n";
    }

    public static BinOpInstruction add(Llvm.Type type, String left, String right, String lvalue) {
        return new BinOpInstruction("add", type, left, right, lvalue);
    }

    public static BinOpInstruction sub(Llvm.Type type, String left, String right, String lvalue) {
        return new BinOpInstruction("sub", type, left, right, lvalue);
    }

    public static BinOpInstruction mul(Llvm.Type type, String left, String right, String lvalue) {
        return new BinOpInstruction("mul", type, left, right, lvalue);
    }

    public static BinOpInstruction div(Llvm.Type type, String left, String right, String lvalue) {
        return new BinOpInstruction("sdiv", type, left, right, lvalue);
    }
}

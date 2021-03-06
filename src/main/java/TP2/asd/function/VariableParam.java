package TP2.asd.function;

import TP2.Context;
import TP2.asd.statement.VariableDeclaration;
import TP2.asd.type.Type;
import TP2.llvm.Llvm;

public class VariableParam implements FunctionParam {

    private final Type type;
    private final String ident;

    public VariableParam(Type type, String ident) {
        this.type = type;
        this.ident = ident;
    }

    @Override
    public String getIdent() {
        return ident;
    }

    @Override
    public Llvm.IR toIR(Context context) {
        final Llvm.IR ir = new VariableDeclaration(type, ident).toIR(context);
        final Context.Symbol sym = context.lookupSymbol(ident).orElse(null); // orElse pas possible, mais safe get
        final Llvm.Instruction ir2 = new Llvm.Assignment(type.toLlvmType(), "%" + sym.getIdent(), sym.toString());
        return ir.appendCode(ir2);
    }

    @Override
    public Context.VariableSymbol toVariableSymbol() {
        return new Context.VariableParamSymbol(type, ident);
    }
}

package TP2.asd.function;

import TP2.Context;
import TP2.llvm.Llvm;

public interface FunctionParam {

    Context.VariableSymbol toVariableSymbol();

    String getIdent();

    Llvm.IR toIR(Context context);
}

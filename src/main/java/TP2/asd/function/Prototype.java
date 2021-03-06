package TP2.asd.function;

import TP2.Context;
import TP2.TypeException;
import TP2.asd.type.Type;
import TP2.llvm.Llvm;

import java.util.List;
import java.util.stream.Collectors;

public class Prototype implements IFunction {

    private final List<FunctionParam> params;
    private final Type returnType;
    private final String ident;

    public Prototype(List<FunctionParam> params, Type returnType, String ident) {
        this.params = params;
        this.returnType = returnType;
        this.ident = ident;
    }

    @Override
    public String pp() {
        return "PROTO " + returnType.toString() + " " + ident + "(" +
                params.stream().map(FunctionParam::toString).collect(Collectors.joining(", "))
                + ")";
    }

    @Override
    public Llvm.IR toIR(Context table) throws TypeException {
        if(table.lookupSymbol(ident).isPresent())
            throw new IllegalStateException("Function '" + ident + "' is already prototyped." + "\nat '" + pp() + "'.");

        final List<Context.VariableSymbol> symbols = params.stream()
                .map(FunctionParam::toVariableSymbol).collect(Collectors.toList());
        Context.FunctionSymbol funSymbol = new Context.FunctionSymbol(returnType, ident, symbols, false);
        table.addSymbol(funSymbol);
        return new Llvm.IR(Llvm.empty(), Llvm.empty());
    }

    @Override
    public String getIdent() {
        return ident;
    }

}

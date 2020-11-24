package TP2.asd.function;

import TP2.SymbolTable;
import TP2.TypeException;
import TP2.asd.type.Type;
import TP2.llvm.Llvm;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Prototype implements IFunction {

    private final List<VariableParam> params;
    private final Type returnType;
    private final String ident;

    public Prototype(List<VariableParam> params, Type returnType, String ident) {
        this.params = params;
        this.returnType = returnType;
        this.ident = ident;
    }

    @Override
    public String pp() {
        return "PROTO " + returnType.toString() + " " + ident + "(" +
                params.stream().map(VariableParam::toString).collect(Collectors.joining(", "))
                + ")";
    }

    @Override
    public Llvm.IR toIR(SymbolTable table) throws TypeException {
        if(table.lookup(ident).isPresent())
            throw new IllegalStateException("Function '" + ident + "' is already prototyped." + "\nat '" + pp() + "'.");

        final List<SymbolTable.VariableSymbol> symbols = params.stream()
                .map(VariableParam::toVariableSymbol).collect(Collectors.toList());
        SymbolTable.FunctionSymbol funSymbol = new SymbolTable.FunctionSymbol(returnType, ident, symbols, false);
        table.add(funSymbol);
        return new Llvm.IR(Collections.emptyList(), Collections.emptyList());
    }

}

package TP2.asd.function;

import TP2.Context;
import TP2.asd.type.Type;

public class VariableParam {

    private final Type type;
    private final String ident;

    public VariableParam(Type type, String ident) {
        this.type = type;
        this.ident = ident;
    }

    public String getIdent() {
        return ident;
    }

    public Context.VariableSymbol toVariableSymbol() {
        return new Context.VariableSymbol(type, ident);
    }
}

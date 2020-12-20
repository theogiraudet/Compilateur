package TP2.asd.statement;

import TP2.Context;
import TP2.Context.VariableSymbol;
import TP2.TypeException;
import TP2.asd.type.Type;
import TP2.llvm.Llvm;
import TP2.utils.Utils;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Objects;

public class VariableDeclaration implements Declaration {

    private final String ident;
    private final Type type;

    public VariableDeclaration(Type type, String ident) {
        Objects.requireNonNull(type);
        this.ident = ident;
        this.type = type;
    }

    public String pp(int nbIndent) {
        return Utils.indent(nbIndent) + type.pp() + " " + ident + "\n";
    }

    public Llvm.IR toIR(Context table) throws TypeException, NullPointerException {

        final VariableSymbol variable = new VariableSymbol(type, ident);
        final boolean isAdded = table.addSymbol(variable);

        if (!isAdded)
            throw new IllegalStateException("Variable '" + ident + "' is already defined." + "\nat '" + pp(0) + "'.");

        final Llvm.Declaration instruction = new Llvm.Declaration(type.toLlvmType(), variable.toString());

        return new Llvm.IR(new LinkedList<>(), new LinkedList<>(Collections.singletonList(instruction)));
    }
}

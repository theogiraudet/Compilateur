package TP2.asd.statement;

import TP2.Context;
import TP2.TypeException;
import TP2.asd.type.Array;
import TP2.llvm.Llvm;
import TP2.utils.Utils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;

public class ArrayDeclaration implements Declaration {

    private final String ident;
    private final Array type;

    public ArrayDeclaration(Array type, String ident) {
        Objects.requireNonNull(type);
        this.ident = ident;
        this.type = type;
    }

    public String pp(int nbIndent) {
        return Utils.indent(nbIndent) + type.pp() + " " + ident + "\n";
    }

    public Llvm.IR toIR(Context table) throws TypeException, NullPointerException {

        final Context.VariableSymbol variable = new Context.VariableSymbol(type, ident);
        final boolean isAdded = table.addSymbol(variable);

        if (!isAdded)
            throw new IllegalStateException("Variable '" + ident + "' is already defined." + "\nat '" + pp(0) + "'.");

        final Llvm.Declaration instruction = new Llvm.Declaration(type.toLlvmArray(), variable.toString() + "$array");
        final Llvm.Bitcast bitcast = new Llvm.Bitcast(variable.toString(), type.toLlvmArray(), variable.toString() + "$array");

        return new Llvm.IR(new LinkedList<>(), new LinkedList<>(Arrays.asList(instruction, bitcast)));
    }

}

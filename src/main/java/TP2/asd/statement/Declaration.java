package TP2.asd.statement;

import TP2.SymbolTable;
import TP2.TypeException;
import TP2.asd.expression.Expression;
import TP2.asd.type.Type;
import TP2.llvm.Llvm;
import TP2.SymbolTable.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Optional;

public class Declaration extends Statement {

    private final String ident;
    private final Type type;

    public Declaration(Type type, String ident) {
        Objects.requireNonNull(type);
        this.ident = ident;
        this.type = type;
    }

    @Override
    public String pp() {
        return type.pp() + " " + ident;
    }

    @Override
    public Llvm.IR toIR(SymbolTable table) throws TypeException, NullPointerException {

        final VariableSymbol variable = new VariableSymbol(type, ident);
        final boolean isAdded = table.add(variable);

        if(!isAdded)
            throw new IllegalStateException("Variable '" + ident + "' is already defined." + "\nat '" + pp() + "'.");

        final Llvm.Declaration instruction = new Llvm.Declaration(type.toLlvmType(), variable.toString());

        return new Llvm.IR(new LinkedList<>(), new LinkedList<>(Arrays.asList(instruction)));
    }
}

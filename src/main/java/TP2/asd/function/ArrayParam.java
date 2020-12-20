package TP2.asd.function;

import TP2.Context;
import TP2.asd.statement.VariableDeclaration;
import TP2.asd.type.Array;
import TP2.asd.type.Type;
import TP2.llvm.Llvm;

import java.util.Collections;
import java.util.LinkedList;

public class ArrayParam implements FunctionParam {
    private final Type type;
    private final String ident;

    public ArrayParam(Type type, String ident) {
        this.type = type;
        this.ident = ident;
    }

    @Override
    public String getIdent() {
        return ident;
    }

    @Override
    public Llvm.IR toIR(Context context) {
        // On délègue l'ajout de la variable à la table des symboles à VariableDeclaration, mais on ne veut pas ajouter une nouvelle instruction
        new VariableDeclaration(type, ident).toIR(context);
        final Context.Symbol sym = context.lookupSymbol(ident).orElse(null); // orElse pas possible, mais safe get
        final Llvm.CopyPtr copy = new Llvm.CopyPtr(((Array) type).getType().toLlvmType(), "%" + sym.getIdent(), sym.toString());
        return new Llvm.IR(Llvm.empty(), new LinkedList<>(Collections.singletonList(copy)));
    }

    @Override
    public Context.VariableSymbol toVariableSymbol() {
        return new Context.VariableParamSymbol(type, ident);
    }
}

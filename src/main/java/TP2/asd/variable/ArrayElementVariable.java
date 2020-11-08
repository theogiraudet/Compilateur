package TP2.asd.variable;

import TP2.SymbolTable;
import TP2.TypeException;
import TP2.Utils;
import TP2.asd.expression.Expression;
import TP2.asd.type.Array;
import TP2.llvm.Llvm;

import java.util.Optional;

public class ArrayElementVariable extends Variable {

    private final String ident;
    private final Expression index;

    public ArrayElementVariable(String ident, Expression index) {
        this.ident = ident;
        this.index = index;
    }

    @Override
    public String pp() {
        return ident + '[' + index.pp() + ']';
    }

    @Override
    public Expression.RetExpression toIR(SymbolTable table) {
        final Optional<SymbolTable.Symbol> symbol = table.lookup(ident);

        final SymbolTable.VariableSymbol var = isValid(symbol);

        final Expression.RetExpression ret = index.toIR(table);

        if(!(ret.type instanceof Llvm.Int))
            throw new TypeException("Type mismatch: '" + var.getType().pp() + "' expected, found '" + ret.type, this::pp);

        final Llvm.IR ir = ret.ir;
        final String dest = Utils.newtmp();

        final Llvm.GetElementPtr instruction = new Llvm.GetElementPtr((Llvm.Array) var.getType().toLlvmType(), ret.result, ident, dest);

        // Le type de la variable correspond au type dont le tableau est un agrégat
        return new Expression.RetExpression(ir.appendCode(instruction), ((Array)var.getType()).getType().toLlvmType(), dest);
    }

    private SymbolTable.VariableSymbol isValid(Optional<SymbolTable.Symbol> symbol) {

        if(!symbol.isPresent())
            throw new NullPointerException("Variable '" + ident + "' is not initialized." + "\nat '" + pp() + "'.");

        if(!(symbol.get() instanceof SymbolTable.VariableSymbol))
            throw new TypeException("Identifier '" + ident + "' is not an identifier of variable.", this::pp);

        final SymbolTable.VariableSymbol variable = (SymbolTable.VariableSymbol)symbol.get();

        if(!(variable.getType() instanceof Array))
            throw new TypeException("Identifier '" + ident + "' is not an identifier of array.", this::pp);

        // La vérification de la présence de l'index dans les bornes du tableau se fait à l'exécution et non pas à la compilation

        return variable;
    }
}

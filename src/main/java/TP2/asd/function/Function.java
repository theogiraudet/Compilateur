package TP2.asd.function;

import TP2.SymbolTable;
import TP2.TypeException;
import TP2.asd.statement.Statement;
import TP2.asd.type.Type;
import TP2.llvm.Llvm;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Function implements IFunction {

    private final List<VariableParam> params;
    private final Type returnType;
    private final String ident;
    private final Statement body;

    public Function(List<VariableParam> params, Type returnType, String ident, Statement body) {
        this.params = params;
        this.returnType = returnType;
        this.ident = ident;
        this.body = body;
    }

    @Override
    public String pp() {
        return "FUNC " + returnType.toString() + " " + ident + "(" +
                params.stream().map(VariableParam::toString).collect(Collectors.joining(", "))
                + ")";
    }

    @Override
    public Llvm.IR toIR(SymbolTable table) throws TypeException {
        final Optional<SymbolTable.Symbol> symbolOpt = table.lookup(ident);
        boolean prototyped = false;

        if(symbolOpt.isPresent()) {
            final SymbolTable.Symbol symbol = symbolOpt.get();
            if(!(symbol instanceof SymbolTable.FunctionSymbol))
                throw new TypeException("Identifier '" + ident + "' is not an identifier of function.", this::pp);
            SymbolTable.FunctionSymbol funSymbol = (SymbolTable.FunctionSymbol)symbol;

            if(funSymbol.isDefined())
                throw new IllegalStateException("Function '" + ident + "' is already defined." + "\nat '" + pp() + "'.");

            table.remove(ident);
            prototyped = true;
        }

        addToTable(table);

        final SymbolTable newTable = new SymbolTable(table);
        params.forEach(p -> newTable.add(p.toVariableSymbol()));

        final Llvm.Instruction ins = new Llvm.Function(ident, params.stream().map(VariableParam::toLlvm).collect(Collectors.toList())
                , returnType.toLlvmType(), body.toIR(newTable));

        if(prototyped)
            return new Llvm.IR(Collections.singletonList(ins), Collections.emptyList());
        else
            return new Llvm.IR(Collections.emptyList(), Collections.singletonList(ins));
    }

    private void addToTable(SymbolTable table) {
        final List<SymbolTable.VariableSymbol> symbols = params.stream()
                .map(VariableParam::toVariableSymbol).collect(Collectors.toList());
        table.add(new SymbolTable.FunctionSymbol(returnType, ident, symbols, true));
    }

}

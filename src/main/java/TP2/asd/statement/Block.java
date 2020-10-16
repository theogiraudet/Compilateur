package TP2.asd.statement;

import TP2.SymbolTable;
import TP2.TypeException;
import TP2.llvm.Llvm;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static TP2.Utils.errorWrapper;

public class Block extends Statement {

    private final List<Statement> statements;

    public Block(List<Statement> statements) {
        this.statements = statements;
    }

    @Override
    public String pp() {
        return statements.stream().map(Statement::pp).collect(Collectors.joining("\n"));
    }

    @Override
    public Llvm.IR toIR(SymbolTable table) throws TypeException, NullPointerException {
        // New Table Context
        final SymbolTable newTable = new SymbolTable(table);
        Optional<Llvm.IR> ins = statements.stream()
                .map(s -> errorWrapper(s::toIR, newTable))
                .reduce((acc, ir) -> acc.append(ir));
        return ins.get();
    }
}

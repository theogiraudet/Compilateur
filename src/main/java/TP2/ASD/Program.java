package TP2.ASD;

import TP2.SymbolTable;
import TP2.llvm.Llvm;
import TP2.TypeException;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Program {
    List<Statement> sts; // What a program contains. TODO : change when you extend the language

    public Program(List<Statement> e) {
      this.sts = e;
    }

    // Pretty-printer
    public String pp() {
      return sts.stream().map(Statement::pp).collect(Collectors.joining("\n"));
    }

    // IR generation
    public Llvm.IR toIR() throws TypeException {
      // TODO : change when you extend the language
        final SymbolTable table = new SymbolTable();
        table.add(new SymbolTable.VariableSymbol(new Int(), "y"));
        table.add(new SymbolTable.VariableSymbol(new Int(), "x"));
      // computes the IR of the expression
        Optional<Llvm.IR> ins = sts.stream()
                .map(s -> errorWrapper(s::toIR, table))
                .reduce((acc, ir) -> acc.append(ir));
      //Expression.RetExpression retExpr = e.toIR();
      // add a return instruction
      //Llvm.Instruction ret = new Llvm.Return(retExpr.type, retExpr.result);
      //retExpr.ir.appendCode(ret);

      return ins.get();
    }

    private Llvm.IR errorWrapper(Function<SymbolTable, Llvm.IR> fun, SymbolTable map) {
        try {
            return fun.apply(map);
        } catch(NullPointerException | TypeException e) {
            e.printStackTrace();
            return null;
        }
    }
  }
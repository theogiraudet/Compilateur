package TP2.asd;

import TP2.asd.statement.Statement;
import TP2.SymbolTable;
import TP2.asd.type.Int;
import TP2.llvm.Llvm;
import TP2.TypeException;

public class Program {
    Statement statement; // What a program contains. TODO : change when you extend the language

    public Program(Statement statement) {
      this.statement = statement;
    }

    // Pretty-printer
    public String pp() {
      return statement.pp();
    }

    // IR generation
    public Llvm.IR toIR() throws TypeException, NullPointerException {
      // TODO : change when you extend the language
        final SymbolTable table = new SymbolTable();
        table.add(new SymbolTable.VariableSymbol(new Int(), "y"));
        table.add(new SymbolTable.VariableSymbol(new Int(), "x"));
      // computes the IR of the expression

      //Expression.RetExpression retExpr = e.toIR();
      // add a return instruction
      //Llvm.Instruction ret = new Llvm.Return(retExpr.type, retExpr.result);
      //retExpr.ir.appendCode(ret);

      return statement.toIR(table);
    }


  }
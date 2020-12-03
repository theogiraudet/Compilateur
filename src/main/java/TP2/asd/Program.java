package TP2.asd;

import TP2.asd.function.IFunction;
import TP2.asd.statement.Statement;
import TP2.SymbolTable;
import TP2.asd.type.Int;
import TP2.llvm.Llvm;
import TP2.TypeException;
import TP2.utils.Try;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Program {
    private final List<IFunction> functions;

    public Program(List<IFunction> functions) {
      this.functions = functions;
    }

    // Pretty-printer
    public String pp() {
      return functions.stream().map(IFunction::pp).collect(Collectors.joining("\n"));
    }

    // IR generation
    public Llvm.IR toIR() throws TypeException, NullPointerException {
        final SymbolTable table = new SymbolTable();
      // computes the IR of the expression

      //Expression.RetExpression retExpr = e.toIR();
      // add a return instruction
      //Llvm.Instruction ret = new Llvm.Return(retExpr.type, retExpr.result);
      //retExpr.ir.appendCode(ret);

        final Optional<Try<Llvm.IR>> fun =
                functions.stream()
                        .map(s -> Try.tryThis(() -> s.toIR(table))) // Application de la fonction toIr sur chacune des functions
                        .reduce((acc, ir) -> acc.reduce(ir, Llvm.IR::append)); // Réduction du stream en concaténant toutes les instructions

        // Vérification que toutes les fonctions sont déclarées
        final Optional<SymbolTable.FunctionSymbol> symbol = functions.stream()
                .map(f -> table.lookup(f.getIdent()))
                .filter(Optional::isPresent) // Normalement, toutes les fonctions se trouvent dans la table des symboles, juste pour éviter un dangerous get
                .map(o -> (SymbolTable.FunctionSymbol)o.get())
                .filter(f -> !f.isDefined())
                .findAny();

        if(symbol.isPresent())
            throw new IllegalStateException("Function '" + symbol.get().getIdent() + "' is prototyped but never declared.");

        // Traitement des exceptions sur fun
        if(fun.isPresent() && fun.get().failed())
            fun.get().get();

      return fun.get().get();
    }


  }
package TP2.asd.expression;

import TP2.SymbolTable;
import TP2.TypeException;
import TP2.asd.statement.Statement;
import TP2.asd.type.Type;
import TP2.llvm.Llvm;
import TP2.utils.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FunctionCall implements Statement, Expression {

    private final String ident;
    private final List<Expression> params;

    public FunctionCall(String ident, List<Expression> params) {
        this.ident = ident;
        this.params = params;
    }

    @Override
    public String pp() {
        return ident + '(' + params.stream().map(Expression::pp).collect(Collectors.joining(", ")) + ')';
    }

    @Override
    public RetExpression toIR(SymbolTable table) throws TypeException {
    final Tuple3<Llvm.FunctionCall, Llvm.IR, Type> res = toIrCommon(table);
    final String var = Utils.newtmp();
    final Llvm.Instruction ir = new Llvm.FunctionCallExpr(res._1, var);
    return new RetExpression(res._2.appendCode(ir), res._3, var);
    }

    @Override
    public String pp(int nbIndent) {
        return Utils.indent(nbIndent) + pp();
    }

    @Override
    public Llvm.IR toIr(SymbolTable table) throws TypeException {
        final Tuple3<Llvm.FunctionCall, Llvm.IR, Type> res = toIrCommon(table);
        return res._2.appendCode(res._1);
    }


    private Tuple3<Llvm.FunctionCall, Llvm.IR, Type> toIrCommon(SymbolTable table) {
        final Optional<SymbolTable.Symbol> symbolOpt = table.lookup(ident);
        if(!symbolOpt.isPresent())
            throw new NullPointerException("Function '" + ident + "' is not declared." + "\nat '" + pp() + "'.");

        if(!(symbolOpt.get() instanceof SymbolTable.FunctionSymbol))
            throw new TypeException("Identifier '" + ident + "' is not an identifier of function.", this::pp);

        final SymbolTable.FunctionSymbol symbol = (SymbolTable.FunctionSymbol) symbolOpt.get();

        // Vérification du bon nombre de paramètres
        if(params.size() > symbol.getArguments().size())
            throw new IllegalArgumentException("Too many parameters entered. " + symbol.getArguments().size()
                    + "expected, found " + params.size() + "\nat '" + pp() + "'.");

        if(params.size() < symbol.getArguments().size())
            throw new IllegalArgumentException("Too few parameters entered. " + symbol.getArguments().size()
                    + "expected, found " + params.size() + "\nat '" + pp() + "'.");


        final List<Try<RetExpression>> retsTry = params.stream().map(exp -> Try.tryThis(() ->exp.toIR(table))).collect(Collectors.toList());

        // Propagation des exceptions vers le haut
        Optional<Try<RetExpression>> retError = retsTry.stream().filter(Try::failed).findFirst();
        retError.ifPresent(Try::get);

        final List<RetExpression> rets = retsTry.stream().map(exp -> exp.get()).collect(Collectors.toList());

        // Vérification du bon typage des paramètres
        Optional<Tuple2<Integer, Type>> badTyped = IntStream.range(0, rets.size()) // Récupération du premier élément mal typé
                .mapToObj(i -> Tuple.of(i, rets.get(i).type)) // Zipage de vars avec leur index dans la liste
                .filter(p -> symbol.getArguments().get(p._1).getType().equals(p._2)).findFirst(); // Vérification du bon typage

        if(badTyped.isPresent()) {
            final String getted = badTyped.get()._2.toString();
            final String expected = symbol.getArguments().get(badTyped.get()._1).getType().toString();
            throw new TypeException("Type mismatch: '" + expected + "' expected, found '" + getted + "' for " +
                    symbol.getArguments().get(badTyped.get()._1).getIdent() + ".", this::pp);
        }

        // Récupération des sorties
        final List<Llvm.Variable> vars = rets.stream()
                .map(ret -> new Llvm.Variable(ret.type.toLlvmType(), ret.result))
                .collect(Collectors.toList());

        final Llvm.FunctionCall ir = new Llvm.FunctionCall(vars, ident, symbol.getReturnType().toLlvmType());

        // Concaténation des IR
        final Optional<Llvm.IR> fun = rets.stream()
                .map(ret -> ret.ir)
                .reduce(Llvm.IR::append); // Réduction du stream en concaténant toutes les instructions

        // Ajout de l'appel de fonction
        final Llvm.IR resultIr = fun.orElse(new Llvm.IR(Collections.emptyList(), Collections.emptyList()));

        //return new RetExpression(resultIr, symbol.getReturnType(), );
        return Tuple.of(ir, resultIr, symbol.getReturnType());
    }
}

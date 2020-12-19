package TP2.asd.statement;

import TP2.Context;
import TP2.TypeException;
import TP2.asd.expression.Expression;
import TP2.asd.reference.Reference;
import TP2.asd.type.Readable;
import TP2.llvm.Llvm;
import TP2.utils.Try;
import TP2.utils.Tuple;
import TP2.utils.Tuple2;
import TP2.utils.Utils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Read implements Statement {

    private final List<Reference> idents;

    public Read(List<Reference> idents) {
        this.idents = idents;
    }

    @Override
    public String pp(int nbIndent) {
        return "READ " + idents.stream().map(Reference::pp).collect(Collectors.joining(", "));
    }

    @Override
    public Llvm.IR toIr(Context table) throws TypeException, NullPointerException {

        final List<Try<Expression.RetExpression>> rExpT = idents.stream()
                .map(r -> Try.tryThis(() -> r.toIR(table))).collect(Collectors.toList());

        final Optional<Try<Expression.RetExpression>> error = rExpT.stream().filter(Try::failed).findAny();

        // Si une erreur a été relevée, on l'a fait remonter (ident inexistant ou ident pas de variable)
        error.ifPresent(Try::get);

        final List<Expression.RetExpression> rExp = rExpT.stream().map(Try::get).collect(Collectors.toList());

        final Optional<Tuple2<Reference, Expression.RetExpression>> badType = IntStream.range(0, idents.size())
                .mapToObj(i -> Tuple.of(idents.get(i), rExp.get(i)))
                .filter(t -> !(t._2.type instanceof Readable)).findFirst();

        // Si l'une des variables n'est pas du type qui lui permet l'écriture, on renvoie une exception
        if (badType.isPresent())
            throw new TypeException("Type '" + badType.get()._2.type.pp() + "' of variable '" + badType.get()._1.pp()
                    + "'is not a readable type.", () -> pp(0));

        // String du scanf
        final String str = rExp.stream().map(r -> ((Readable) r.type).toReadable()).collect(Collectors.joining(" "));

        // Paramètres de la fonction scanf
        final List<Llvm.Variable> vars = rExp.stream().map(r -> new Llvm.Variable(r.type.toLlvmType(), r.result)).collect(Collectors.toList());

        final String label = Utils.newglob(".fmt");
        final Utils.LLVMStringConstant constant = Utils.stringTransform(str);
        final Llvm.Instruction insStr = new Llvm.DefineString(label, constant.str, constant.length);
        final Llvm.IR ins = rExp.stream().map(exp -> exp.ir).reduce(Llvm.IR::append).orElse(new Llvm.IR(Llvm.empty(), Llvm.empty()));
        ins.appendCode(new Llvm.Read(new Llvm.StringL(constant.length), label, vars));

        return ins.appendHeader(insStr);
    }
}

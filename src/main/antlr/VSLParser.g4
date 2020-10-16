parser grammar VSLParser;

options {
  language = Java;
  tokenVocab = VSLLexer;
}

@header {
  package TP2;

  import java.util.stream.Collectors;
  import java.util.Arrays;
  import java.util.LinkedList;
  import java.util.function.BiFunction;
  import TP2.ASD.*;
}


// TODO : other rules

program returns [TP2.ASD.Program out]
@init { List<Statement> list = new LinkedList<>(); }
    :  statement? EOF  // TODO : change when you extend the language
    ;

statement returns  [TP2.ASD.Statement out]
    : i=IDENT AFFECT e=expression {$out = new Assignment($i.text,$e.out);}
    | b=block {$out = new Block($b.out)}
    ;

block returns [TP2.ASD.Block out]
@init { List<Statement> list = new LinkedList<>(); }
    :   LB (s=statement { list.add($s.out); })* RB { $out = new TP2.ASD.Block(list}
    ;

expression returns [TP2.ASD.Expression out]
    : l=factor o=lpop  { $out = $o.op.apply($l.out,$o.out); }
    | f=factor { $out = $f.out; }
    // TODO : that's all?
    ;

lpop returns [BiFunction<TP2.ASD.Expression, TP2.ASD.Expression, TP2.ASD.Expression> op, TP2.ASD.Expression out]
    : PLUS e = expression { $op = (exp1,exp2) -> new AddExpression(exp1,exp2); $out=$e.out; }
    | MINUS e = expression { $op = (exp1,exp2) -> new SubExpression(exp1,exp2); $out=$e.out; }
    ;

factor returns [TP2.ASD.Expression out]
    : p = primary o = hpop { $out = $o.op.apply($p.out, $o.out); }
    | p = primary { $out = $p.out; }
    ;

hpop returns [BiFunction<Expression, Expression, Expression> op, Expression out]
    : TIMES f = factor { $op = (exp1,exp2) -> new MulExpression(exp1,exp2); $out = $f.out; }
    | DIVIDE f = factor { $op = (exp1,exp2) -> new DivExpression(exp1,exp2); $out = $f.out; }
    ;

primary returns [TP2.ASD.Expression out]
    : LP e = expression RP { $out=$e.out; }
    | INTEGER { $out = new TP2.ASD.IntegerExpression($INTEGER.int); }
    // TODO : that's all?
    ;

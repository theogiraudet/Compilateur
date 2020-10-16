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
  import TP2.asd.statement.*;
  import TP2.asd.expression.*;
  import TP2.asd.type.*;
  import TP2.asd.*;
}


// TODO : other rules

program returns [Program out]
    :  s=statement EOF {$out = new Program($s.out); } // TODO : change when you extend the language
    ;

decAndStat returns [DecAndStat out]        //this rule provides to have only one declaration when a if isn't a block
    : s=statement {$out = $s.out;}
    | d=declaration {$out = $d.out;}
    ;

statement returns  [Statement out]
    : i=IDENT AFFECT e=expression { $out = new Assignment($i.text,$e.out); }
    | b=block { $out = $b.out; }
    ;

block returns [Block out]
@init { List<Statement> list = new LinkedList<>(); }
    :   LB (s=decAndStat { list.add($s.out); })* RB { $out = new Block(list); }
    ;

declaration returns [LinkedList<Declaration> out]
@init { List<Declaration> list = new LinkedList(); }
    : t=type v1=var { list.add(new Declaration($t.out,$v1.out); }
      (COMMA v2=var { list.add(new Declaration($t.out,$v2.out); })* { $out=list }
    ;

type returns [Type out]
    : INT { new Int(); }
    ;

var returns [Var out]
    : i=IDENT { $out=new Var($i.text); }
    ;

expression returns [Expression out]
    : l=factor o=lpop  { $out = $o.op.apply($l.out,$o.out); }
    | f=factor { $out = $f.out; }
    // TODO : that's all?
    ;

lpop returns [BiFunction<Expression, Expression, Expression> op, Expression out]
    : PLUS e = expression { $op = (exp1,exp2) -> new AddExpression(exp1,exp2); $out=$e.out; }
    | MINUS e = expression { $op = (exp1,exp2) -> new SubExpression(exp1,exp2); $out=$e.out; }
    ;

factor returns [Expression out]
    : p = primary o = hpop { $out = $o.op.apply($p.out, $o.out); }
    | p = primary { $out = $p.out; }
    ;

hpop returns [BiFunction<Expression, Expression, Expression> op, Expression out]
    : TIMES f = factor { $op = (exp1,exp2) -> new MulExpression(exp1,exp2); $out = $f.out; }
    | DIVIDE f = factor { $op = (exp1,exp2) -> new DivExpression(exp1,exp2); $out = $f.out; }
    ;

primary returns [Expression out]
    : LP e = expression RP { $out=$e.out; }
    | INTEGER { $out = new IntegerExpression($INTEGER.int); }
    // TODO : that's all?
    ;

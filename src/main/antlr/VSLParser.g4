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
    :  s = statement EOF { $out = new Program($s.out); } // TODO : change when you extend the language
    ;

statement returns  [Statement out]
    : i = IDENT AFFECT e = expression { $out = new Assignment($i.text, $e.out); }
    | b = block { $out = $b.out; }
    | IF e = expression THEN s1 = statement
        ((ELSE s2 = statement  { $out = new If($e.out,$s1.out,$s2.out) ; } )
        | { $out = new If($e.out,$s1.out) ; } ) FI
    | WHILE e = expression DO s = statement  DONE { $out = new While($e.out,$s.out) ; }
    ;

block returns [Block out]
@init { List<Statement> listStat = new LinkedList<>(); }
    :   LB d = declaration (s = statement { listStat.add($s.out); })+ RB { $out = new Block($d.out, listStat); }
    ;

declaration returns [List<Declaration> out]
@init { List<Declaration> list = new LinkedList(); }
    : (t = type id = IDENT {list.add(new Declaration($t.out, $id.text)); } listDeclaration[$t.out, list])? { $out = list; }
    ;

listDeclaration [Type typ, List<Declaration> list] returns [List<Declaration> out]
    :   (COMMA id= IDENT
               ( LSB i = INTEGER RSB { list.add(new Declaration(new Array(typ, $i.int), $id.text)); }
               |  {list.add(new Declaration(typ, $id.text)); } )
        )*
    ;

type returns [Type out]
    : INT { new Int(); }
    ;

expression returns [Expression out]
    : l = factor o = lpop  { $out = $o.op.apply($l.out,$o.out); }
    | f = factor { $out = $f.out; }
    // TODO : that's all?
    ;

lpop returns [BiFunction<Expression, Expression, Expression> op, Expression out]
    : PLUS e = expression { $op = (exp1,exp2) -> new AddExpression(exp1,exp2); $out = $e.out; }
    | MINUS e = expression { $op = (exp1,exp2) -> new SubExpression(exp1,exp2); $out = $e.out; }
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
    | id=IDENT (( LSB  in = INTEGER RSB { $out = new ArrayExpression($id.text, $in.int); })
               | { $out = new VarExpression($id.text); })
    // TODO : that's all?
    ;

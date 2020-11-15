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
  import TP2.asd.reference.*;
}


// TODO : other rules

program returns [Program out]
    :  s = statement EOF { $out = new Program($s.out); } // TODO : change when you extend the language
    ;

statement returns  [Statement out]
    : v = variable AFFECT e = expression { $out = new Assignment($v.out, $e.out); }
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
    : (t = type id = IDENT
                     ( LSB i = INTEGER RSB { list.add(new Declaration(new Array($t.out, $i.int), $id.text)); }
                     | {list.add(new Declaration($t.out, $id.text)); } )
      listDeclaration[$t.out, list])? { $out = list; }
    ;

listDeclaration [Type typ, List<Declaration> list] returns [List<Declaration> out]
    :   (COMMA id= IDENT
               ( LSB i = INTEGER RSB { list.add(new Declaration(new Array(typ, $i.int), $id.text)); }
               |  {list.add(new Declaration(typ, $id.text)); } )
        )*
    ;

variable returns [Reference out]
    :  i = IDENT ( LSB e = expression RSB {$out = new ArrayElementReference($i.text,$e.out); }
                 | {$out = new VariableReference($i.text);}  )
    ;


type returns [Type out]
    : INT { $out = new Int(); }
    ;

expression returns [Expression out]
@init { Expression exp = null; }
    : f1 = factor { exp = $f1.out; }
      ( PLUS  f2 = factor {exp = new AddExpression(exp, $f2.out); }
      | MINUS f2 = factor {exp = new SubExpression(exp, $f2.out); }
    )* { $out = exp; }
    ;

factor returns [Expression out]
@init { Expression exp = null; }
    : p1 = primary { exp = $p1.out; }
      ( TIMES  p2 = primary { exp = new MulExpression(exp, $p2.out); }
      | DIVIDE p2 = primary { exp = new DivExpression(exp, $p2.out); }
    )* { $out = exp; }
    ;

primary returns [Expression out]
    : LP e = expression RP { $out=$e.out; }
    | INTEGER { $out = new IntegerExpression($INTEGER.int); }
    | id=IDENT (( LSB  in = expression RSB { $out = new Dereference(new ArrayElementReference($id.text, $in.out)); })
               | { $out = new Dereference(new VariableReference($id.text)); })
    // TODO : that's all?
    ;
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
  import TP2.asd.function.*;
  import TP2.asd.type.Void;
}

program returns [Program out]
    :  i = iFunction EOF { $out = new Program($i.out); }
    ;

iFunction returns [List<IFunction> out]
@init {List<IFunction> list = new LinkedList();}
    : (p = decProto { list.add($p.out); } | d = decFunc { list.add($d.out); })+ { $out = list; }
    ;

decFunc returns [IFunction out]
@init {List<FunctionParam> list = new LinkedList();}
    :   FUNC t = retType i = IDENT LP (p = params { list = $p.out; })? RP s = statement { $out = new Function(list, $t.out, $i.text, $s.out); }
    ;

decProto returns [IFunction out]
@init {List<FunctionParam> list = new LinkedList();}
    : PROTO t = retType i = IDENT LP (p = params {list = $p.out; })? RP {$out = new Prototype(list, $t.out, $i.text); }
    ;

params returns [List<FunctionParam> out]
@init { List<FunctionParam> list = new LinkedList(); }
    :  (id = IDENT (LSB RSB { list.add(new ArrayParam(new Array(new Int()), $id.text)) ;}
                 | { list.add(new VariableParam(new Int(), $id.text)); } )
        (listParams[list])? {$out = list;})
    ;

listParams [List<FunctionParam> list]
    :  (COMMA id = IDENT
                      (LSB i = INTEGER RSB { list.add(new ArrayParam(new Array(new Int(), $i.int), $id.text)); }
                      |  { list.add(new VariableParam(new Int(), $id.text)); })
       )+
    ;

statement returns  [Statement out]
    : b = block { $out = $b.out; }
    | v = variable AFFECT e = expression { $out = new Assignment($v.out, $e.out); }
    | IF e = expression THEN s1 = statement
        ((ELSE s2 = statement  { $out = new If($e.out, $s1.out, $s2.out); } )
        | { $out = new If($e.out, $s1.out) ; } ) FI
    | WHILE e = expression DO s = statement DONE { $out = new While($e.out,$s.out); }
    | f = funcCall { $out = $f.out; }
    | RETURN (e = expression { $out = new Return($e.out); } | { $out = new Return(); })
    | p=print  {$out = $p.out;}
    | r=read  {$out = $r.out;}
    ;

block returns [Block out]
@init { List<Statement> listStat = new LinkedList<>(); }
    :   LB d = declaration (s = statement { listStat.add($s.out); })+ RB { $out = new Block($d.out, listStat); }
    ;

declaration returns [List<Declaration> out]
@init { List<Declaration> list = new LinkedList(); }
    : (t = type id = IDENT
                     ( LSB i = INTEGER RSB { list.add(new ArrayDeclaration(new Array($t.out, $i.int), $id.text)); }
                     | {list.add(new VariableDeclaration($t.out, $id.text)); } )
      listDeclaration[$t.out, list])? { $out = list; }
    ;

listDeclaration [Type typ, List<Declaration> list] returns [List<Declaration> out]
    :   (COMMA id= IDENT
               ( LSB i = INTEGER RSB { list.add(new ArrayDeclaration(new Array(typ, $i.int), $id.text)); }
               |  { list.add(new VariableDeclaration(typ, $id.text)); } )
        )*
    ;

variable returns [Reference out]
    :  i = IDENT ( LSB e = expression RSB {$out = new ArrayElementReference($i.text,$e.out); }
                 | { $out = new VariableReference($i.text); }  )
    ;

retType returns [Type out]
    : t=type { $out = $t.out; } | VOID { $out = new Void(); }
    ;

type returns [Type out]
    : INT { $out = new Int(); }
    ;

print returns [Print out]
@init { List<Expression> list = new LinkedList(); }
    :  PRINT e = printable { list.add($e.out); } (COMMA t = printable { list.add($t.out); })* { $out = new Print(list); }
    ;

printable returns [Expression out]
    :   (s = TEXT { $out=new StringExpression($s.text); } | e = expression { $out = $e.out; })
    ;

read returns [Read out]
@init { List<Reference> list = new LinkedList(); }
    :   READ v = variable { list.add($v.out); } (COMMA r = variable { list.add($r.out); })* { $out = new Read(list); }
    ;

expression returns [Expression out]
@init { Expression exp = null; }
    : (f1 = factor { exp = $f1.out; }
      ( PLUS  f2 = factor {exp = new AddExpression(exp, $f2.out); }
      | MINUS f2 = factor {exp = new SubExpression(exp, $f2.out); }
    )* { $out = exp; })
    | f = funcCall {$out = $f.out;}
    ;

funcCall returns [FunctionCall out]
@init {List<Expression> list = new LinkedList();} :
    i=IDENT LP (e = listExp { list = $e.out; } )? RP { $out = new FunctionCall($i.text, list); }
    ;

listExp returns [List<Expression> out]
@init {List<Expression> list = new LinkedList<>();}
    : e = expression { list.add($e.out); } (COMMA a = expression { list.add($a.out); } )* { $out = list;}
    ;

factor returns [Expression out]
@init { Expression exp = null; }
    : p1 = primary { exp = $p1.out; }
      ( TIMES  p2 = primary { exp = new MulExpression(exp, $p2.out); }
      | DIVIDE p2 = primary { exp = new DivExpression(exp, $p2.out); }
    )* { $out = exp; }
    ;

primary returns [Expression out]
    : LP e = expression RP { $out = $e.out; }
    | INTEGER { $out = new IntegerExpression($INTEGER.int); }
    | id=IDENT (( LSB  in = expression RSB { $out = new Dereference(new ArrayElementReference($id.text, $in.out)); })
               | { $out = new Dereference(new VariableReference($id.text)); })
    ;
parser grammar VSLParser;

options {
  language = Java;
  tokenVocab = VSLLexer;
}

@header {
  package TP2;

  import java.util.stream.Collectors;
  import java.util.Arrays;
  import java.util.function.BiFunction;
  import TP2.ASD.*;
}


// TODO : other rules

program returns [TP2.ASD.Program out]
    : e=expression EOF { $out = new TP2.ASD.Program($e.out); } // TODO : change when you extend the language
    ;

expression returns [TP2.ASD.Expression out]
    : l=factor b=binop  { $out = $b.operation.apply($l.out,$b.out); }
    | f=factor { $out = $f.out; }
    // TODO : that's all?
    ;

binop returns [BiFunction<TP2.ASD.Expression,TP2.ASD.Expression,TP2.ASD.Expression> operation, TP2.ASD.Expression out]
    : PLUS e=expression {$operation = ( (exp1,exp2) -> new AddExpression(exp1,exp2)); $out=$e.out;}
    | MINUS e=expression {$operation = ( (exp1,exp2) -> new SubExpression(exp1,exp2)); $out=$e.out;}
    ;

factor returns [TP2.ASD.Expression out]
    : p=primary { $out = $p.out; }
    // TODO : that's all?
    ;

primary returns [TP2.ASD.Expression out]
    : INTEGER { $out = new TP2.ASD.IntegerExpression($INTEGER.int); }
    // TODO : that's all?
    ;

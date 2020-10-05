package TP2;

import java.nio.file.Paths;
import java.io.IOException;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import TP2.ASD.Program;

public class Main {
  public static void main(String[] args) {
    try {
      // Set input
      CharStream input;
      if(args.length == 0) {
        // From standard input
        input = CharStreams.fromStream(System.in);
      }
      else {
        // From file set in first argument
        input = CharStreams.fromPath(Paths.get(args[0]));
      }

      // Instantiate Lexer
      VSLLexer lexer = new VSLLexer(input);
      CommonTokenStream tokens = new CommonTokenStream(lexer);

      // Instantiate Parser
      VSLParser parser = new VSLParser(tokens);

      // Parse
      Program ast = parser.program().out;

      // Pretty-print the program (to debug parsing, if you implemented it!)
      // System.err.println(ast.pp());

      // Compute LLVM IR from the ast
      try {
        Llvm.IR ir = ast.toIR();

        // Output LLVM IR
        System.out.println(ir);
      } catch(TypeException e) {
        e.printStackTrace(); // Useful for developping, not for the ``end users''!
        System.err.println(e.getMessage());
      }
    } catch(IOException e) {
      e.printStackTrace();
    }
  }
}

package TP2.llvm;

import TP2.IllegalFormatException;

import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;

// This file contains a simple LLVM IR representation
// and methods to generate its string representation

public class Llvm {
  static public class IR {
    private final List<Instruction> header; // IR instructions to be placed before the code (global definitions)
    private final List<Instruction> code;   // main code

    public IR(List<Instruction> header, List<Instruction> code) {
      this.header = header;
      this.code = code;
    }

    // append an other IR
    public IR append(IR other) {
      header.addAll(other.header);
      code.addAll(other.code);
      return this;
    }

    // append a code instruction
    public IR appendCode(Instruction inst) {
      code.add(inst);
      return this;
    }

    // append a code header
    public IR appendHeader(Instruction inst) {
      header.add(inst);
      return this;
    }

    public List<Instruction> getCode() {
      return new LinkedList<>(code);
    }

    public List<Instruction> getHeader() {
      return new LinkedList<>(header);
    }

    // Final string generation
    public String toString() {
      // This header describe to LLVM the target
      // and declare the external function printf
      StringBuilder r = new StringBuilder("; Target\n" +
        "target triple = \"x86_64-unknown-linux-gnu\"\n" +
        "; External declaration of the printf function\n" +
        "declare i32 @printf(i8* noalias nocapture, ...)\n" +
        "\n; Actual code begins\n\n");

      for(Instruction inst: header)
        r.append(inst);

      r.append("\n\n");


      for(Instruction inst: code)
        r.append(inst);


      return r.toString();
    }
  }

  // Returns a new empty list of instruction, handy
  static public List<Instruction> empty() {
    return new ArrayList<>();
  }


  // LLVM Types
  static public abstract class Type {
    public abstract String toString();

    @Override
    public abstract boolean equals(Object o);
  }

  static public class Int extends Type {
    public String toString() {
      return "i32";
    }

    @Override
    public boolean equals(Object o) {
      return o instanceof Int;
    }


  }

  static public class Array extends Type {
    private final Type type;
    private final int size;

    public Array(Type type, int size) {
      this.type = type;
      this.size = size;
    }

    public String toString() { return "[" + size + " x " + type.toString() + "]"; }

    @Override
    public boolean equals(Object o) {
      if(o instanceof Array) {
        Array array = (Array) o;
        return array.size == size && array.type.equals(type);
      }
      return false;
    }
  }

  static public class Void extends Type {

    @Override
    public String toString() {
      return "void";
    }

    @Override
    public boolean equals(Object o) {
      return o instanceof Void;
    }
  }

  static public class StringL extends Type {
    private final int size;

    public StringL(int size) {
      // La taille du String LLVM est la même que celle du String classique + 1 pour le caractère null
      // En Java, \n comme \t est considéré dans un String comme étant un seul caractère, il n'y a donc pas besoin
      // de faire -1 par \n trouvé
      this.size = size + 1;
    }

    @Override
    public String toString() {
      return "[" + size + " x i8]";
    }

    @Override
    public boolean equals(Object o) {
      if (!(o instanceof StringL))
        return false;
      final StringL str = (StringL) o;
      return str.size == size;
    }
  }

  static public class Variable {

    private final Type type;
    private final String ident;

    public Variable(Type type, String ident) {
      this.type = type;
      this.ident = ident;
    }

    public String toString() { return type.toString() + " " + ident; }
  }

  // LLVM IR Instructions
  static public abstract class Instruction {
    public abstract String toString();
  }

  static public class Return extends Instruction {
    Type type;
    String value;

    public Return(Type type, String value) {
      this.type = type;
      this.value = value;
    }

    public String toString() {
      return "ret " + type + ((type instanceof Void) ? "" : " " + value) + "\n";
    }
  }

  static public class Assignment extends Instruction {

    Type type;
    String value;
    String destination;

    public Assignment(Type type, String value, String destination) {
      this.type = type;
      this.value = value;
      this.destination = destination;
    }

    @Override
    public String toString() {
      return "store " + type + " " + value + ", " + type + "* " + destination + '\n';
    }
  }

  static public class Declaration extends Instruction {

    Type type;
    String destination;

    public Declaration(Type type, String destination) {
      this.type = type;
      this.destination = destination;
    }

    @Override
    public String toString() {
      return destination + " = alloca " + type.toString() + '\n';
    }
  }

  static public class GetElementPtr extends Instruction {

    private final Array type;
    private final String position;
    private final String source;
    private final String destination;

    public GetElementPtr(Array type, String position, String source, String destination) {
      this.type = type;
      this.position = position;
      this.source = source;
      this.destination = destination;
    }

    @Override
    public String toString() {
      return destination + " = getelementptr " + type.toString() + ", " + type.toString() + "* " + source + ", i64 0, i32 " + position + "\n";
    }
  }

  static public class Load extends Instruction {

    Type type;
    String destination;
    String value;

    public Load(Type type, String destination, String value) {
      this.type = type;
      this.destination = destination;
      this.value = value;
    }

    @Override
    public String toString() {
      //%val = load i32, i32* %ptr
      return destination + " = load " + type + ", " + type + "* " + value + "\n";
    }
  }

  static public class Conditional extends Instruction {

    private final String value;
    private final int equals;
    private final String destination;
    private final Comparator comparator;

    public enum Comparator {
      EQUAL("eq"),
      NOT_EQUAL("ne");

      private String value;

      Comparator(String ne) { value = ne; }
    }

    public Conditional(String value, int equals, String destination, Comparator comparator) {
      this.value = value;
      this.equals = equals;
      this.destination = destination;
      this.comparator = comparator;
    }

    @Override
    public String toString() {
      //%cond = icmp eq i32 %a, %b
      return destination + " = icmp " + comparator.value + " i32 " + value + ", " + equals + '\n';
    }
  }

  static public class ConditionalBranch extends Instruction {

    private final String cond;
    private final String ifLabel;
    private final String elseLabel;

    public ConditionalBranch(String cond, String ifLabel, String elseLabel) {
      this.cond = cond;
      this.ifLabel = ifLabel;
      this.elseLabel = elseLabel;
    }

    @Override
    public String toString() {
      //br i1 <cond>, label <iftrue>, label <iffalse>
      return "br i1 " + cond + ", label %" + ifLabel + ", label %" + elseLabel + '\n';
    }
  }

  static public class UnconditionalBranch extends Instruction {

    private final String label;

    public UnconditionalBranch(String label) {
      this.label = label;
    }

    @Override
    public String toString() {
      return "br label %" + label + "\n";
    }
  }

  static public class Label extends Instruction {

    private final String label;

    public Label(String label) {
      this.label = label;
    }

    @Override
    public String toString() {
      return label + ":\n";
    }
  }

  static public class Function extends Instruction {

    private final String ident;
    private final List<Variable> params;
    private final Type returnType;
    private final List<Instruction> ins;

    public Function(String ident, List<Variable> params, Type returnType, List<Instruction> ins) {
      this.ident = ident;
      this.params = params;
      this.returnType = returnType;
      this.ins = ins;
    }

    public String toString() {
      return "define " + returnType.toString() + " @" + ident + "(" +
              params.stream().map(Variable::toString).collect(Collectors.joining(", "))
              + ") {\n" + ins.stream().map(Instruction::toString).collect(Collectors.joining()) + "}\n";
    }
  }

  static public class FunctionCall extends Instruction {
    private final List<Variable> params;
    private final String ident;
    private final Type returnType;

    public FunctionCall(List<Variable> params, String ident, Type returnType) {
      this.params = params;
      this.ident = ident;
      this.returnType = returnType;
    }

    @Override
    public String toString() {
      return "call " + returnType.toString() + " @" + ident + "(" + params.stream().map(Variable::toString).collect(Collectors.joining(", ")) + ")\n";
    }
  }

  static public class FunctionCallExpr extends Instruction {
    private final FunctionCall functionCall;
    private final String dest;

    public FunctionCallExpr(FunctionCall functionCall, String dest) {
      this.functionCall = functionCall;
      this.dest = dest;
    }

    @Override
    public String toString() {
      return dest + " = " + functionCall.toString();
    }
  }

  static public class DefineString extends Instruction {

    private final String label;
    private final String string;

    public DefineString(String label, String string) {
      this.label = label;
      this.string = string;
    }

    @Override
    public String toString() {
      return label + " = global [" + string.length() + 1 + " x i8] c\"" + formatString(string) + "\"\n";
    }
  }

  static public class Print extends Instruction {

    final StringL type;
    final String str;
    final List<Variable> results;

    public Print(StringL type, String str, List<Variable> results) {
      this.type = type;
      this.str = str;
      this.results = results;
    }

    @Override
    public String toString() {
      return "call i32 (i8*, ...) @printf(i8* getelementptr inbounds (" + type.toString() + ", " +
              type.toString() + "* " + str + ", i64 0, i64 0), " + results.stream().map(Variable::toString).collect(Collectors.joining(", ")) + ")";
    }
  }

  static public class Read extends Instruction {

    final StringL type;
    final String str;
    final List<Variable> results;

    public Read(StringL type, String str, List<Variable> results) {
      this.type = type;
      this.str = str;
      this.results = results;
    }

    @Override
    public String toString() {
      return "call i32 (i8*, ...) @scanf(i8* getelementptr inbounds (" + type.toString() + ", " +
              type.toString() + "* " + str + ", i64 0, i64 0), " + results.stream().map(Variable::toString).collect(Collectors.joining(", ")) + ")";
    }
  }


  /**
   * @param str un string à convertir
   * @return un string formaté pour LLVM
   * @throws IllegalFormatException si le string possède un caractère non affichable différent de '\n'
   */
  private static String formatString(String str) {
    // Transformation de tous les caractères inférieurs à 32 en hexadécimal
    final OptionalInt badFormat = CharBuffer.wrap(str.toCharArray()).chars().filter(c -> c < 32 && c != '\n').findFirst();
    if (badFormat.isPresent())
      throw new IllegalFormatException("String only accept printable character and '\n'. " + "\\u" + Integer.toHexString(badFormat.getAsInt()) + " is not allowed.");

    final String string = CharBuffer.wrap(str.toCharArray()).chars()
            .mapToObj(c -> c < 32 ? String.format("%02X", c) : Character.toString((char) c))
            .collect(Collectors.joining()) + "\00";
    return string;
  }
}

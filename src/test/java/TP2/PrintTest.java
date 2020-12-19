package TP2;

import TP2.asd.Program;
import TP2.llvm.Llvm;
import TP2.utils.Utils;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PrintTest {
    private VSLParser parser;

    private Program createParser(String input) throws RecognitionException {
        VSLLexer lexer = new VSLLexer(CharStreams.fromString(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        parser = new VSLParser(tokens);

        Utils.reset();
        return parser.program().out;
    }

    @DisplayName("print d'une String")
    @Test
    public void print0() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Print/testPrint0V.vsl");
        final String result1 = "@.fmt1 = global [18 x i8] c\"Je suis un ananas\\00\"";
        final String result2 = "define void @main() {\n" +
                "call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([18 x i8], [18 x i8]* @.fmt1, i64 0, i64 0))\n" +
                "}";
        final Program p = createParser(vsl);
        final Llvm.IR ir = p.toIR();
        assertTrue(ir.toString().contains(result1));
        assertTrue(ir.toString().contains(result2));
    }

    @DisplayName("print d'une variable")
    @Test
    public void print1() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Print/testPrint1V.vsl");
        final String result = "@.fmt1 = global [3 x i8] c\"%d\\00\"";//TODO: résultat en LLVM
        final String result2 = "define void @main() {\n" +
                "%b2.a = alloca i32\n" +
                "store i32 0, i32* %b2.a\n" +
                "%tmp1 = load i32, i32* %b2.a\n" +
                "call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([3 x i8], [3 x i8]* @.fmt1, i64 0, i64 0), i32 %tmp1)\n" +
                "}";
        final Program p = createParser(vsl);
        final String ir = p.toIR().toString();
        assertTrue(ir.contains(result));
        assertTrue(ir.contains(result2));
    }

    @DisplayName("print d'une variable non initialisée")
    @Test
    public void print2() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Print/testPrint2F.vsl");
        final Program p = createParser(vsl);
        assertThrows(NullPointerException.class, p::toIR);
    }

    @DisplayName("print d'un élément de tableau")
    @Test
    public void print3() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Print/testPrint3V.vsl");
        final String result = "@.fmt1 = global [3 x i8] c\"%d\\00\"";
        final String result2 = "define void @main() {\n" +
                "%b2.a = alloca [5 x i32]\n" +
                "%tmp1 = getelementptr [5 x i32], [5 x i32]* %b2.a, i64 0, i32 1\n" +
                "store i32 0, i32* %tmp1\n" +
                "%tmp2 = getelementptr [5 x i32], [5 x i32]* %b2.a, i64 0, i32 1\n" +
                "%tmp3 = load i32, i32* %tmp2\n" +
                "call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([3 x i8], [3 x i8]* @.fmt1, i64 0, i64 0), i32 %tmp3)\n" +
                "}";
        final String ir = createParser(vsl).toIR().toString();
        assertTrue(ir.contains(result));
        assertTrue(ir.contains(result2));
    }

    @DisplayName("print d'un appel de fonction")
    @Test
    public void print4() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Print/testPrint4V.vsl");
        final String result = "@.fmt1 = global [3 x i8] c\"%d\\00\"";
        final String result2 = "define i32 @aplusb(i32 %a, i32 %b) {\n" +
                "%b1.a = alloca i32\n" +
                "%b1.b = alloca i32\n" +
                "store i32 %a, i32* %b1.a\n" +
                "store i32 %b, i32* %b1.b\n" +
                "%tmp1 = load i32, i32* %b1.a\n" +
                "%tmp2 = load i32, i32* %b1.b\n" +
                "%tmp3 = add i32 %tmp1, %tmp2\n" +
                "ret i32 %tmp3\n" +
                "}\n" +
                "define void @main() {\n" +
                "%tmp4 = call i32 @aplusb(i32 1, i32 2)\n" +
                "call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([3 x i8], [3 x i8]* @.fmt1, i64 0, i64 0), i32 %tmp4)\n" +
                "ret void\n" +
                "}";
        final String ir = createParser(vsl).toIR().toString();
        assertTrue(ir.contains(result));
        assertTrue(ir.contains(result2));
    }

    @DisplayName("print d'une série de string et variables")
    @Test
    void print5() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Print/testPrint5V.vsl");
        final String result = "@.fmt1 = global [31 x i8] c\"Je suis un ananas qui a %dans\\0A\\00\"";
        final String result2 = "define void @main() {\n" +
                "%b2.x = alloca i32\n" +
                "store i32 4, i32* %b2.x\n" +
                "%tmp1 = load i32, i32* %b2.x\n" +
                "call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([31 x i8], [31 x i8]* @.fmt1, i64 0, i64 0), i32 %tmp1)\n" +
                "ret void\n" +
                "}";
        final String ir = createParser(vsl).toIR().toString();
        assertTrue(ir.contains(result));
        assertTrue(ir.contains(result2));
    }

    @DisplayName("print d'une série mais un tableau")
    @Test
    void print6() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Print/testPrint6F.vsl");
        final Program p = createParser(vsl);
        assertThrows(TypeException.class, p::toIR);
    }

    @DisplayName("print d'une variable avec pour nom 'tmp'")
    @Test
    void print7() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Print/testPrint7V.vsl");
        final String result = "@.fmt1 = global [3 x i8] c\"%d\\00\"";
        final String result2 = "define void @main() {\n" +
                "%b2.tmp = alloca i32\n" +
                "%b2.test = alloca i32\n" +
                "store i32 5, i32* %b2.tmp\n" +
                "%tmp1 = add i32 1, 3\n" +
                "%tmp2 = add i32 %tmp1, 4\n" +
                "store i32 %tmp2, i32* %b2.test\n" +
                "%tmp3 = load i32, i32* %b2.tmp\n" +
                "call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([3 x i8], [3 x i8]* @.fmt1, i64 0, i64 0), i32 %tmp3)\n" +
                "}";
        final String ir = createParser(vsl).toIR().toString();
        System.out.println(ir);
        assertTrue(ir.contains(result));
        assertTrue(ir.contains(result2));
    }
}

package TP2;

import TP2.asd.Program;
import TP2.utils.Utils;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ReadTest {
    private VSLParser parser;

    private Program createParser(String input) throws RecognitionException {
        VSLLexer lexer = new VSLLexer(CharStreams.fromString(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        parser = new VSLParser(tokens);

        Utils.reset();
        return parser.program().out;
    }

    @DisplayName("read d'une variable")
    @Test
    void read0() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Read/testRead0V.vsl");
        final String result = "@.fmt1 = global [3 x i8] c\"%d\\00\"";
        final String result2 = "define void @main() {\n" +
                "%b2.x = alloca i32\n" +
                "call i32 (i8*, ...) @scanf(i8* getelementptr inbounds ([3 x i8], [3 x i8]* @.fmt1, i64 0, i64 0), i32* %b2.x)\n" +
                "%tmp1 = load i32, i32* %b2.x\n" +
                "call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([3 x i8], [3 x i8]* @.fmt2, i64 0, i64 0), i32 %tmp1)\n" +
                "ret void\n" +
                "}";
        final String ir = createParser(vsl).toIR().toString();
        assertTrue(ir.contains(result));
        assertTrue(ir.contains(result2));
    }

    @DisplayName("read d'un élément de tableau")
    @Test
    void read1() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Read/testRead1V.vsl");
        final String result = "@.fmt1 = global [3 x i8] c\"%d\\00\"";
        final String result2 = "define void @main() {\n" +
                "%b2.x = alloca [2 x i32]\n" +
                "%tmp1 = getelementptr [2 x i32], [2 x i32]* %b2.x, i64 0, i32 1\n" +
                "call i32 (i8*, ...) @scanf(i8* getelementptr inbounds ([3 x i8], [3 x i8]* @.fmt1, i64 0, i64 0), i32* %tmp1)\n" +
                "}\n";
        final String ir = createParser(vsl).toIR().toString();
        assertTrue(ir.contains(result));
        assertTrue(ir.contains(result2));
    }

    @DisplayName("read d'une liste de variables")
    @Test
    void read2() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Read/testRead2V.vsl");
        final String result = "@.fmt1 = global [6 x i8] c\"%d %d\\00\"";
        final String result2 = "define void @main() {\n" +
                "%b2.x = alloca i32\n" +
                "%b2.y = alloca i32\n" +
                "%b2.z = alloca i32\n" +
                "call i32 (i8*, ...) @scanf(i8* getelementptr inbounds ([6 x i8], [6 x i8]* @.fmt1, i64 0, i64 0), i32* %b2.x, i32* %b2.z)\n" +
                "}\n";
        final String ir = createParser(vsl).toIR().toString();
        System.out.println(ir);
        assertTrue(ir.contains(result));
        assertTrue(ir.contains(result2));
    }

    @DisplayName("read d'une liste de var mais il y a un tableau")
    @Test
    void read3() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Read/testRead3F.vsl");
        final Program p = createParser(vsl);
        assertThrows(TypeException.class, p::toIR);
    }

    @DisplayName("read d'une var non déclarée")
    @Test
    void read4() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Read/testRead4F.vsl");
        final Program p = createParser(vsl);
        assertThrows(NullPointerException.class, p::toIR);
    }

    @DisplayName("read d'une liste de var mais il y a un appel de fonction")
    @Test
    public void read5() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Read/testRead5F.vsl");
        createParser(vsl);
        assertEquals(3, parser.getNumberOfSyntaxErrors());
    }
}

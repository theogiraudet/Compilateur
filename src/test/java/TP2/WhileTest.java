package TP2;

import TP2.asd.Program;
import TP2.utils.Utils;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WhileTest {

    private VSLParser parser;

    private Program createParser(String input) throws RecognitionException {
        input = "FUNC INT main() {\n" + input + "\n}";
        VSLLexer lexer = new VSLLexer(CharStreams.fromString(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        parser = new VSLParser(tokens);

        Utils.reset();
        return parser.program().out;
    }

    @DisplayName("While simple en ligne")
    @Test
    public void while1() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/While/testWhile1V.vsl");
        final String result = "%2.n = alloca i32\n" +
                "store i32 10, i32* %2.n\n" +
                "While1:\n" +
                "%tmp1 = load i32, i32* %2.n\n" +
                "%tmp2 = icmp ne i32 %tmp1, 0\n" +
                "br i1 %tmp2, label %Do1, label %Done1\n" +
                "Do1:\n" +
                "%tmp3 = load i32, i32* %2.n\n" +
                "%tmp4 = sub i32 %tmp3, 1\n" +
                "store i32 %tmp4, i32* %2.n\n" +
                "br label %While1\n" +
                "Done1:";
        Program p = createParser(vsl);
        assertTrue(p.toIR().toString().contains(result));
    }

    @DisplayName("While absence done")
    @Test
    public void while2() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/While/testWhile2F.vsl");
        createParser(vsl);
        assertEquals(1, parser.getNumberOfSyntaxErrors());
    }

    @DisplayName("While absence do")
    @Test
    public void while3() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/While/testWhile3F.vsl");
        createParser(vsl);
        assertEquals(1, parser.getNumberOfSyntaxErrors());
    }

    @DisplayName("While absence condition")
    @Test
    public void while4() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/While/testWhile4F.vsl");
        createParser(vsl);
        assertEquals(2, parser.getNumberOfSyntaxErrors());
    }

    @DisplayName("While absence corps")
    @Test
    public void while5() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/While/testWhile5F.vsl");
        createParser(vsl);
        assertEquals(1, parser.getNumberOfSyntaxErrors());
    }

    @DisplayName("While simple en bloc avec une expression en condition")
    @Test
    public void while6() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/While/testWhile6V.vsl");
        final String result = "%2.n = alloca i32\n" +
                "store i32 10, i32* %2.n\n" +
                "While1:\n" +
                "%tmp1 = load i32, i32* %2.n\n" +
                "%tmp2 = sub i32 %tmp1, 1\n" +
                "%tmp3 = icmp ne i32 %tmp2, 0\n" +
                "br i1 %tmp3, label %Do1, label %Done1\n" +
                "Do1:\n" +
                "%tmp4 = load i32, i32* %2.n\n" +
                "%tmp5 = sub i32 %tmp4, 1\n" +
                "store i32 %tmp5, i32* %2.n\n" +
                "br label %While1\n" +
                "Done1:"   ;
        Program p = createParser(vsl);
        assertTrue(p.toIR().toString().contains(result));
    }

    @DisplayName("While absence corps en bloc")
    @Test
    public void while7() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/While/testWhile7F.vsl");
        createParser(vsl);
        assertEquals(1, parser.getNumberOfSyntaxErrors());
    }

    @DisplayName("While imbriqués")
    @Test
    public void while8() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/While/testWhile8V.vsl");
        final String result = "%2.n = alloca i32\n" +
                "%2.j = alloca i32\n" +
                "store i32 10, i32* %2.n\n" +
                "store i32 7, i32* %2.j\n" +
                "While1:\n" +
                "%tmp1 = load i32, i32* %2.n\n" +
                "%tmp2 = icmp ne i32 %tmp1, 0\n" +
                "br i1 %tmp2, label %Do1, label %Done1\n" +
                "Do1:\n" +
                "%tmp3 = load i32, i32* %2.n\n" +
                "%tmp4 = sub i32 %tmp3, 1\n" +
                "store i32 %tmp4, i32* %2.n\n" +
                "While2:\n" +
                "%tmp5 = load i32, i32* %2.j\n" +
                "%tmp6 = icmp ne i32 %tmp5, 0\n" +
                "br i1 %tmp6, label %Do2, label %Done2\n" +
                "Do2:\n" +
                "%tmp7 = load i32, i32* %2.j\n" +
                "%tmp8 = sub i32 %tmp7, 1\n" +
                "store i32 %tmp8, i32* %2.j\n" +
                "br label %While2\n" +
                "Done2:\n" +
                "br label %While1\n" +
                "Done1:"   ;
        Program p = createParser(vsl);
        assertTrue(p.toIR().toString().contains(result));
    }
}

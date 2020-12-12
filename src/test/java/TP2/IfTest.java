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

public class IfTest {

    private VSLParser parser;

    private Program createParser(String input) throws RecognitionException {
        input = "FUNC INT main() {\n" + input + "\n}";
        VSLLexer lexer = new VSLLexer(CharStreams.fromString(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        parser = new VSLParser(tokens);

        Utils.reset();
        return parser.program().out;
    }

    @DisplayName("If simple statement et condition simple")
    @Test
    public void if1() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/If/testIf0V.vsl");
        final String result = "%2.n = alloca i32\n" +
                "store i32 1, i32* %2.n\n" +
                "%tmp1 = load i32, i32* %2.n\n" +
                "%tmp2 = icmp ne i32 %tmp1, 0\n" +
                "br i1 %tmp2, label %If1, label %End1\n" +
                "If1:\n" +
                "%tmp3 = load i32, i32* %2.n\n" +
                "%tmp4 = add i32 %tmp3, 1\n" +
                "store i32 %tmp4, i32* %2.n\n" +
                "End1:";
        Program p = createParser(vsl);
        assertTrue(p.toIR().toString().contains(result));
    }

    @DisplayName("If absence condition")
    @Test
    public void if2() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/If/testIf1F.vsl");
        createParser(vsl);
        assertEquals(1, parser.getNumberOfSyntaxErrors());
    }

    @DisplayName("If condition avec variables non initialisée")
    @Test
    public void if3() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/If/testIf2F.vsl");
        Program p = createParser(vsl);
        assertThrows(NullPointerException.class, p::toIR);
    }

    @DisplayName("If avec bloc vide")
    @Test
    public void if4() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/If/testIf4F.vsl");
        createParser(vsl);
        assertEquals(2, parser.getNumberOfSyntaxErrors());
    }

    @DisplayName("If condition complexe avec bloc")
    @Test
    public void if5() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/If/testIf5V.vsl");
        final String result = "%2.a = alloca i32\n" +
                "%tmp1 = load i32, i32* %2.a\n" +
                "%tmp2 = add i32 %tmp1, 1\n" +
                "%tmp3 = icmp ne i32 %tmp2, 0\n" +
                "br i1 %tmp3, label %If1, label %End1\n" +
                "If1:\n" +
                "%tmp4 = load i32, i32* %2.a\n" +
                "%tmp5 = add i32 %tmp4, 1\n" +
                "store i32 %tmp5, i32* %2.a\n" +
                "End1:";
        Program p = createParser(vsl);
        assertTrue(p.toIR().toString().contains(result));
    }

    @DisplayName("If bloc avec else statement")
    @Test
    public void if6() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/If/testIf6V.vsl");
        final String result = "%2.a = alloca i32\n" +
                "%tmp1 = load i32, i32* %2.a\n" +
                "%tmp2 = icmp ne i32 %tmp1, 0\n" +
                "br i1 %tmp2, label %If1, label %Else1\n" +
                "If1:\n" +
                "%tmp3 = load i32, i32* %2.a\n" +
                "%tmp4 = add i32 %tmp3, 1\n" +
                "store i32 %tmp4, i32* %2.a\n" +
                "br label %End1\n" +
                "Else1:\n" +
                "%tmp5 = load i32, i32* %2.a\n" +
                "%tmp6 = sub i32 %tmp5, 1\n" +
                "store i32 %tmp6, i32* %2.a\n" +
                "End1:";
        Program p = createParser(vsl);
        assertTrue(p.toIR().toString().contains(result));
    }

    @DisplayName("If bloc avec else statement")
    @Test
    public void if7() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/If/testIf7V.vsl");
        final String result = "%2.a = alloca i32\n" +
                "%tmp1 = load i32, i32* %2.a\n" +
                "%tmp2 = icmp ne i32 %tmp1, 0\n" +
                "br i1 %tmp2, label %If1, label %Else1\n" +
                "If1:\n" +
                "%tmp3 = load i32, i32* %2.a\n" +
                "%tmp4 = add i32 %tmp3, 1\n" +
                "store i32 %tmp4, i32* %2.a\n" +
                "br label %End1\n" +
                "Else1:\n" +
                "%tmp5 = load i32, i32* %2.a\n" +
                "%tmp6 = sub i32 %tmp5, 1\n" +
                "store i32 %tmp6, i32* %2.a\n" +
                "End1:";
        Program p = createParser(vsl);
        assertTrue(p.toIR().toString().contains(result));
    }

    @DisplayName("If statement avec else bloc")
    @Test
    public void if8() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/If/testIf8V.vsl");
        final String result = "%2.a = alloca i32\n" +
                "%tmp1 = load i32, i32* %2.a\n" +
                "%tmp2 = icmp ne i32 %tmp1, 0\n" +
                "br i1 %tmp2, label %If1, label %Else1\n" +
                "If1:\n" +
                "%tmp3 = load i32, i32* %2.a\n" +
                "%tmp4 = add i32 %tmp3, 1\n" +
                "store i32 %tmp4, i32* %2.a\n" +
                "br label %End1\n" +
                "Else1:\n" +
                "%tmp5 = load i32, i32* %2.a\n" +
                "%tmp6 = sub i32 %tmp5, 1\n" +
                "store i32 %tmp6, i32* %2.a\n" +
                "End1:";
        Program p = createParser(vsl);
        assertTrue(p.toIR().toString().contains(result));
    }

    @DisplayName("Else vide")
    @Test
    public void if9() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/If/testIf9F.vsl");
        createParser(vsl);
        assertEquals(1, parser.getNumberOfSyntaxErrors());
    }

    @DisplayName("If vide")
    @Test
    public void if10() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/If/testIf10F.vsl");
        createParser(vsl);
        assertEquals(1, parser.getNumberOfSyntaxErrors());
    }

    @DisplayName("Imbrication if")
    @Test
    public void if11() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/If/testIf11V.vsl");
        final String result = "%2.a = alloca i32\n" +
                "%tmp1 = load i32, i32* %2.a\n" +
                "%tmp2 = icmp ne i32 %tmp1, 0\n" +
                "br i1 %tmp2, label %If1, label %Else1\n" +
                "If1:\n" +
                "%tmp3 = load i32, i32* %2.a\n" +
                "%tmp4 = add i32 %tmp3, 1\n" +
                "store i32 %tmp4, i32* %2.a\n" +
                "br label %End1\n" +
                "Else1:\n" +
                "%tmp5 = load i32, i32* %2.a\n" +
                "%tmp6 = add i32 %tmp5, 1\n" +
                "%tmp7 = icmp ne i32 %tmp6, 0\n" +
                "br i1 %tmp7, label %If2, label %End2\n" +
                "If2:\n" +
                "%tmp8 = load i32, i32* %2.a\n" +
                "%tmp9 = sub i32 %tmp8, 1\n" +
                "store i32 %tmp9, i32* %2.a\n" +
                "End2:\n" +
                "End1:";
        Program p = createParser(vsl);
        assertTrue(p.toIR().toString().contains(result));
    }

    @DisplayName("Mauvais type pour condition")
    @Test
    public void if12() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/If/testIf12F.vsl");
        Program p = createParser(vsl);
        assertThrows(TypeException.class, p::toIR);
    }
}

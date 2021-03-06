package TP2;

import TP2.asd.Program;
import TP2.utils.Utils;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AssignementTest {

    private Program createParser(String input) throws RecognitionException {
        input = "FUNC INT main() {\n" + input + "\n}";
        VSLLexer lexer = new VSLLexer(CharStreams.fromString(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        VSLParser parser = new VSLParser(tokens);
        Utils.reset();
        return parser.program().out;
    }

    @DisplayName("Affectation à une variable non déclarée")
    @Test
    public void assignement1() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Affect/testAffect0F.vsl");
        Program p = createParser(vsl);
        assertThrows(NullPointerException.class, p::toIR);
    }

    @DisplayName("Affectation correcte")
    @Test
    public void assignement2() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Affect/testAffect1V.vsl");
        final String result = "%b2.y = alloca i32\n" +
                "%tmp1 = mul i32 4, 7\n" +
                "%tmp2 = add i32 5, %tmp1\n" +
                "store i32 %tmp2, i32* %b2.y";
        Program prog = createParser(vsl);
        assertTrue(prog.toIR().toString().contains(result));
    }

    @DisplayName("Affectation simple + référence à droite")
    @Test
    public void assignement3() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Affect/testAffect2V.vsl");
        final String result = "%b2.i = alloca i32\n" +
                "store i32 1, i32* %b2.i\n" +
                "%tmp1 = load i32, i32* %b2.i\n" +
                "%tmp2 = add i32 %tmp1, 1\n" +
                "store i32 %tmp2, i32* %b2.i";
        Program prog = createParser(vsl);
        assertTrue(prog.toIR().toString().contains(result));
    }

    @DisplayName("Affectation à une case d'un tableau et depuis une case de tableau")
    @Test
    public void assignement4() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Affect/testAffect3V.vsl");
        final String result = "%b2.b$array = alloca [4 x i32]\n" +
                "%b2.b = bitcast [4 x i32]* %b2.b$array to i32*\n" +
                "%b2.c = alloca i32\n" +
                "%tmp1 = getelementptr inbounds i32, i32* %b2.b, i32 2\n" +
                "store i32 5, i32* %tmp1\n" +
                "%tmp2 = getelementptr inbounds i32, i32* %b2.b, i32 5\n" +
                "%tmp3 = load i32, i32* %tmp2\n" +
                "%tmp4 = add i32 %tmp3, 1\n" +
                "store i32 %tmp4, i32* %b2.c\n";
        Program prog = createParser(vsl);
        assertTrue(prog.toIR().toString().contains(result));
    }

    @DisplayName("Affectation à une variable déclarée dans un bloc différent")
    @Test
    public void assignement5() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Affect/testAffect4V.vsl");
        final String result = "%b2.c$array = alloca [5 x i32]\n" +
                "%b2.c = bitcast [5 x i32]* %b2.c$array to i32*\n" +
                "%b3.b = alloca i32\n" +
                "store i32 4, i32* %b3.b\n" +
                "%tmp1 = getelementptr inbounds i32, i32* %b2.c, i32 4\n" +
                "store i32 4, i32* %tmp1";
        Program prog = createParser(vsl);
        assertTrue(prog.toIR().toString().contains(result));
    }

    @DisplayName("Affectation à une variable dans un bloc différent avec erreur de typage")
    @Test
    public void assignement6() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Affect/testAffect5F.vsl");
        Program prog = createParser(vsl);
        assertThrows(TypeException.class, prog::toIR);
    }

    @DisplayName("Affectation avec pour indice de tableau une expression")
    @Test
    public void assignement7() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Affect/testAffect6V.vsl");
        final String result = "%b2.t$array = alloca [5 x i32]\n" +
                "%b2.t = bitcast [5 x i32]* %b2.t$array to i32*\n" +
                "%b2.i = alloca i32\n" +
                "store i32 3, i32* %b2.i\n" +
                "%tmp1 = load i32, i32* %b2.i\n" +
                "%tmp2 = add i32 %tmp1, 1\n" +
                "%tmp3 = getelementptr inbounds i32, i32* %b2.t, i32 %tmp2\n" +
                "%tmp4 = load i32, i32* %b2.i\n" +
                "store i32 %tmp4, i32* %tmp3\n" +
                "%tmp5 = load i32, i32* %b2.i\n" +
                "%tmp6 = add i32 %tmp5, 1\n" +
                "%tmp7 = getelementptr inbounds i32, i32* %b2.t, i32 %tmp6\n" +
                "%tmp8 = load i32, i32* %tmp7\n" +
                "store i32 %tmp8, i32* %b2.i";
        Program prog = createParser(vsl);
        assertTrue(prog.toIR().toString().contains(result));
    }

    @DisplayName("Affectation avec erreur de type")
    @Test
    public void assignement8() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Affect/testAffect7F.vsl");
        Program prog = createParser(vsl);
        assertThrows(TypeException.class, prog::toIR);
    }

    @DisplayName("Affectation avec une référence d'un tableau qui n'est pas un entier")
    @Test
    public void assignement9() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Affect/testAffect8F.vsl");
        Program prog = createParser(vsl);
        assertThrows(TypeException.class, prog::toIR);
    }

    @DisplayName("Affectation à un ident qui n'existe pas")
    @Test
    public void assignement10() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Affect/testAffect9F.vsl");
        Program prog = createParser(vsl);
        assertThrows(NullPointerException.class, prog::toIR);
    }
}
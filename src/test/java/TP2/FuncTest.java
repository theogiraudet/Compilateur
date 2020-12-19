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

class FuncTest {

    private Program createParser(String input) throws RecognitionException {
        VSLLexer lexer = new VSLLexer(CharStreams.fromString(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        VSLParser parser = new VSLParser(tokens);

        Utils.reset();
        return parser.program().out;
    }

    @DisplayName("Fonction simple")
    @Test
    void func0() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Fonction/testFunc0V.vsl");
        final String result = "define i32 @aplusb(i32 %a, i32 %b) {\n"
                + "%b1.a = alloca i32\n"
                + "%b1.b = alloca i32\n"
                + "store i32 %a, i32* %b1.a\n"
                + "store i32 %b, i32* %b1.b\n"
                + "%tmp1 = load i32, i32* %b1.a\n"
                + "%tmp2 = load i32, i32* %b1.b\n"
                + "%tmp3 = add i32 %tmp1, %tmp2\n"
                + "ret i32 %tmp3\n"
                + "}";
        Program p = createParser(vsl);
        assertTrue(p.toIR().toString().contains(result));
    }

    @DisplayName("Appel à un paramètre inexistant variable non déclarée")
    @Test
    void func1() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Fonction/testFunc1F.vsl");
        Program p = createParser(vsl);
        assertThrows(NullPointerException.class, p::toIR);
    }

    @DisplayName("Appel à une fonction non déclarée avant")
    @Test
    void func2() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Fonction/testFunc2F.vsl");
        Program p = createParser(vsl);
        assertThrows(NullPointerException.class, p::toIR);
    }

    @DisplayName("Appel à une fonction déclarée + aucun return")
    @Test
    void func3() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Fonction/testFunc3V.vsl");
        final String result = "define i32 @aplusb(i32 %a, i32 %b) {\n"
                + "%b1.a = alloca i32\n"
                + "%b1.b = alloca i32\n"
                + "store i32 %a, i32* %b1.a\n"
                + "store i32 %b, i32* %b1.b\n"
                + "%tmp1 = load i32, i32* %b1.a\n"
                + "%tmp2 = load i32, i32* %b1.b\n"
                + "%tmp3 = add i32 %tmp1, %tmp2\n"
                + "ret i32 %tmp3\n"
                + "}\n"
                + "define i32 @somme(i32 %c, i32 %a, i32 %b) {\n"
                + "%b1.c = alloca i32\n"
                + "%b1.a = alloca i32\n"
                + "%b1.b = alloca i32\n"
                + "store i32 %c, i32* %b1.c\n"
                + "store i32 %a, i32* %b1.a\n"
                + "store i32 %b, i32* %b1.b\n"
                + "%tmp4 = load i32, i32* %b1.a\n"
                + "%tmp5 = load i32, i32* %b1.b\n"
                + "%tmp6 = call i32 @aplusb(i32 %tmp4, i32 %tmp5)\n"
                + "store i32 %tmp6, i32* %b1.c\n"
                + "%tmp7 = load i32, i32* %b1.c\n"
                + "ret i32 %tmp7\n"
                + "}\n";
        Program p = createParser(vsl);
        assertTrue(p.toIR().toString().contains(result));
    }

    @DisplayName("Appel à une fonction non déclarée mais prototypée avant")
    @Test
    void func4() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Fonction/testFunc4V.vsl");
        final String result = "define i32 @a() {\n"
                + "ret i32 1\n"
                + "}\n"
                + "define i32 @b() {\n"
                + "%tmp1 = call i32 @a()\n"
                + "ret i32 %tmp1\n"
                + "}\n";
        Program p = createParser(vsl);
        assertTrue(p.toIR().toString().contains(result));
    }

    @DisplayName("Appel avec mauvais nombre de paramètres")
    @Test
    void func5() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Fonction/testFunc5F.vsl");
        Program p = createParser(vsl);
        assertThrows(IllegalArgumentException.class, p::toIR);
    }

    @DisplayName("Déclaration d'un proto après la fonction")
    @Test
    void func6() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Fonction/testFunc6F.vsl");
        Program p = createParser(vsl);
        assertThrows(IllegalStateException.class, p::toIR);
    }

    @DisplayName("Appel à une fonction non déclarée")
    @Test
    void func7() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Fonction/testFunc7F.vsl");
        Program p = createParser(vsl);
        assertThrows(NullPointerException.class, p::toIR);
    }

    @DisplayName("Déclaration d'un proto mais fonction jamais déclarée")
    @Test
    void func8() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Fonction/testFunc8F.vsl");
        Program p = createParser(vsl);
        assertThrows(IllegalStateException.class, p::toIR);
    }

    @DisplayName("Proto avec des param différents de la fonction")
    @Test
    void func9() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Fonction/testFunc9F.vsl");
        Program p = createParser(vsl);
        assertThrows(NullPointerException.class, p::toIR);
    }

}

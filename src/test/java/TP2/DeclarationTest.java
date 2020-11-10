package TP2;

import TP2.asd.Program;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class DeclarationTest {

    private VSLParser parser;

    private Program createParser(String input) throws RecognitionException {
        VSLLexer lexer = new VSLLexer(CharStreams.fromString(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        parser = new VSLParser(tokens);

        SymbolTable.reset();
        return parser.program().out;
    }

    @Test
    public void assignment() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Decl/testDecl0V.vsl");
        final String result = "%1.a = alloca i32\n" +
                "%1.b = alloca i32\n" +
                "%1.c = alloca i32\n" +
                "%1.d = alloca [5 x i32]\n" +
                "%1.e = alloca i32\n" +
                "%1.f = alloca i32\n" +
                "store i32 1, i32* %1.a";

        Program prog = createParser(vsl);
        assertTrue(prog.toIR().toString().contains(result));
    }

    @DisplayName("Declaration sans ident")
    @Test
    public void declaration2() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Decl/testDecl1F.vsl");
        createParser(vsl);
        assertEquals(1, parser.getNumberOfSyntaxErrors());
    }

    @DisplayName("Declaration finissant par un virgule et non un ident")
    @Test
    public void declaration3() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Decl/testDecl2F.vsl");
        createParser(vsl);
        assertEquals(1, parser.getNumberOfSyntaxErrors());
    }

    @DisplayName("Declaration avec un tableau dont le crochet de fermeture manque")
    @Test
    public void declaration4() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Decl/testDecl3F.vsl");
        createParser(vsl);
        assertEquals(1, parser.getNumberOfSyntaxErrors());
    }

    @Test
    public void declaration5() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Decl/testDecl4V.vsl");
        final String result = "%1.a = alloca i32\n" +
                "store i32 15, i32* %1.a";

        Program prog = createParser(vsl);
        assertTrue(prog.toIR().toString().contains(result));
    }

    @DisplayName("Declaration avec deux variables du même nom")
    @Test
    public void declaration6() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Decl/testDecl5F.vsl");
        Program prog = createParser(vsl);
        assertThrows(IllegalStateException.class, prog::toIR);
    }

    @DisplayName("Declaration avec deux variables, dont un tableau, du même nom")
    @Test
    public void declaration7() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Decl/testDecl6F.vsl");
        Program prog = createParser(vsl);
        assertThrows(IllegalStateException.class, prog::toIR);
    }

    @DisplayName("Declaration d'une variable avec pour nom un entier")
    @Test
    public void declaration8() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Decl/testDecl7F.vsl");
        createParser(vsl);
        // Une erreur pour l'ident, et une pour l'affectation
        assertEquals(2, parser.getNumberOfSyntaxErrors());
    }

    @DisplayName("Declaration de deux variables ayant le même nom mais dans deux blocs différents")
    @Test
    public void declaration9() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Decl/testDecl8V.vsl");
        final String result = "%1.a = alloca i32\n" +
                              "%2.a = alloca i32\n" +
                              "store i32 1, i32* %2.a";
        Program p = createParser(vsl);
        assertTrue(p.toIR().toString().contains(result));
    }
}

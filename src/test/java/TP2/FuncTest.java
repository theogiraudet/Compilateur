package TP2;

import TP2.asd.Program;
import TP2.utils.Utils;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

public class FuncTest {
    private VSLParser parser;

    private Program createParser(String input) throws RecognitionException {
        VSLLexer lexer = new VSLLexer(CharStreams.fromString(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        parser = new VSLParser(tokens);

        SymbolTable.reset();
        Utils.reset();
        return parser.program().out;
    }

    @BeforeEach
    public void reset() {
        SymbolTable.reset();
    }

    @DisplayName("Fonction simple")
    @Test
    public void func0() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Fonction/testFunc0V.vsl");
        final String result = ""; //TODO: écrire le résultat llvm attendu
        Program p = createParser(vsl);
        assertTrue(p.toIR().toString().contains(result));
    }

    @DisplayName("Appel à un parametre inexistant/ variable non déclarée")
    @Test
    public void func1() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Fonction/testFunc1F.vsl");
        Program p = createParser(vsl);
        assertEquals(1, parser.getNumberOfSyntaxErrors());
    }

    @DisplayName("Appel à une fonction déclarée après sans prototype avant")
    @Test
    public void func2() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Fonction/testFunc2F.vsl");
        Program p = createParser(vsl);
        assertEquals(1, parser.getNumberOfSyntaxErrors());
    }

    @DisplayName("Appel à une fonction déclarée + aucun return")
    @Test
    public void func3() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Fonction/testFunc3V.vsl");
        final String result = ""; //TODO: écrire le résultat llvm attendu
        Program p = createParser(vsl);
        assertTrue(p.toIR().toString().contains(result));
    }

    @DisplayName("Appel à une fonction non déclarée mais prototypée avant")
    @Test
    public void func4() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Fonction/testFunc4V.vsl");
        final String result = ""; //TODO: écrire le résultat llvm attendu
        Program p = createParser(vsl);
        assertTrue(p.toIR().toString().contains(result));
    }

    @DisplayName("Appel avec mauvais nombre de paramètres")
    @Test
    public void func5() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Fonction/testFunc5F.vsl");
        Program p = createParser(vsl);
        assertEquals(1, parser.getNumberOfSyntaxErrors());
    }

    @DisplayName("Déclaration d'un proto après la fonction")
    @Test
    public void func6() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Fonction/testFunc6F.vsl");
        Program p = createParser(vsl);
        assertEquals(1, parser.getNumberOfSyntaxErrors());
    }

    @DisplayName("Appel à une fonction non déclarée")
    @Test
    public void func7() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Fonction/testFunc7F.vsl");
        Program p = createParser(vsl);
        assertEquals(1, parser.getNumberOfSyntaxErrors());
    }

    @DisplayName("Déclaration d'un proto mais fonction jamais déclarée")
    @Test
    public void func8() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Fonction/testFunc8F.vsl");
        Program p = createParser(vsl);
        assertEquals(1, parser.getNumberOfSyntaxErrors());
    }
}

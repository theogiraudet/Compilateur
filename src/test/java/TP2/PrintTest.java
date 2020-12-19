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

public class PrintTest {
    private VSLParser parser;

    private Program createParser(String input) throws RecognitionException {
        input = "FUNC INT main() {\n" + input + "\n}";
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
        final String result = "";//TODO: résultat en LLVM
        Program p = createParser(vsl);
        assertTrue(p.toIR().toString().contains(result));
    }

    @DisplayName("print d'une variable")
    @Test
    public void print1() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Print/testPrint1V.vsl");
        final String result = "";//TODO: résultat en LLVM
        Program p = createParser(vsl);
        assertTrue(p.toIR().toString().contains(result));
    }

    @DisplayName("print d'une variable non initialisée")
    @Test
    public void print2() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Print/testPrint2F.vsl");
        createParser(vsl);
        assertEquals(1, parser.getNumberOfSyntaxErrors());
    }

    @DisplayName("print d'un élément de tableau")
    @Test
    public void print3() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Print/testPrint3F.vsl");
        createParser(vsl);
        assertEquals(1, parser.getNumberOfSyntaxErrors());
    }

    @DisplayName("print d'un appel de fonction")
    @Test
    public void print4() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Print/testPrint4F.vsl");
        createParser(vsl);
        assertEquals(1, parser.getNumberOfSyntaxErrors());
    }

    @DisplayName("print d'une série de string et variables")
    @Test
    public void print5() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Print/testPrint5V.vsl");
        final String result = "";//TODO: résultat en LLVM
        Program p = createParser(vsl);
        assertTrue(p.toIR().toString().contains(result));
    }

    @DisplayName("print d'une série mais avec un élément de tableau")
    @Test
    public void print6() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Print/testPrint6F.vsl");
        createParser(vsl);
        assertEquals(1, parser.getNumberOfSyntaxErrors());
    }
}

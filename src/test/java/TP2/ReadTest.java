package TP2;

import TP2.asd.Program;
import TP2.utils.Utils;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;

import java.io.IOException;

public class ReadTest {
    private VSLParser parser;

    private Program createParser(String input) throws RecognitionException {
        input = "FUNC INT main() {\n" + input + "\n}";
        VSLLexer lexer = new VSLLexer(CharStreams.fromString(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        parser = new VSLParser(tokens);

        Utils.reset();
        return parser.program().out;
    }

    @DisplayName("read d'une variable")
    @Test
    public void read0() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Read/testRead0V.vsl");
        final String result = "";//TODO: résultat en LLVM
        Program p = createParser(vsl);
        assertTrue(p.toIR().toString().contains(result));
    }

    @DisplayName("read d'un élément de tableau")
    @Test
    public void read1() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Read/testRead1F.vsl");
        createParser(vsl);
        assertEquals(1, parser.getNumberOfSyntaxErrors());
    }

    @DisplayName("read d'une liste de variables")
    @Test
    public void read2() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Read/testRead2V.vsl");
        final String result = "";//TODO: résultat en LLVM
        Program p = createParser(vsl);
        assertTrue(p.toIR().toString().contains(result));
    }

    @DisplayName("read d'une liste de var mais il y a un élément de tableau")
    @Test
    public void read3() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Read/testRead3F.vsl");
        createParser(vsl);
        assertEquals(1, parser.getNumberOfSyntaxErrors());
    }

    @DisplayName("read d'une var non déclarée")
    @Test
    public void read4() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Read/testRead4F.vsl");
        createParser(vsl);
        assertEquals(1, parser.getNumberOfSyntaxErrors());
    }

    @DisplayName("read d'une liste de var mais il y a un appel de fonction")
    @Test
    public void read5() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Read/testRead5F.vsl");
        createParser(vsl);
        assertEquals(1, parser.getNumberOfSyntaxErrors());
    }
}

package TP2;

import TP2.asd.Program;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeclarationTest {

    private VSLParser parser;

    private Program createParser(String input) throws RecognitionException {
        VSLLexer lexer = new VSLLexer(CharStreams.fromString(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        parser = new VSLParser(tokens);

        return parser.program().out;
    }

    @Test
    public void assignment() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/testDecl0V.vsl");
        Program prog = createParser(vsl);
        System.out.println(prog.toIR());
    }
}

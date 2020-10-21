package TP2;

import TP2.asd.Program;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AssignementTest {

    private VSLParser parser;

    public Program createParser(String input) throws RecognitionException {
        VSLLexer lexer = new VSLLexer(CharStreams.fromString(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        parser = new VSLParser(tokens);

        return parser.program().out;
    }

    @Test
    public void assignement() {
        final SymbolTable table = new SymbolTable();
        final String INPUT = "a := 5";
        String result = createParser(INPUT).pp();
        assertEquals("a := 5", result);
    }
}

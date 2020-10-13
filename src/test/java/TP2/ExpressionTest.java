package TP2;


import TP2.ASD.Program;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ExpressionTest {

    public Program createParser(String input) throws RecognitionException {
        VSLLexer lexer = new VSLLexer(CharStreams.fromString(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        VSLParser parser = new VSLParser(tokens);
        return parser.program().out;
    }

    @Test
    public void atomicExpression() {
        final String INPUT = "5";
        String result = createParser(INPUT).pp();
        assertEquals(result, "5");
    }

    @Test
    public void atomicExpression2() {
        final String INPUT = "+";
        assertThrows(RecognitionException.class, () -> createParser(INPUT));
    }

}

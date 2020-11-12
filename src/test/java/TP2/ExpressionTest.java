package TP2;


import TP2.asd.expression.Expression;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpressionTest {

    private VSLParser parser;

    public Expression createParser(String input) throws RecognitionException {
        VSLLexer lexer = new VSLLexer(CharStreams.fromString(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        parser = new VSLParser(tokens);

        return parser.expression().out;
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
        createParser(INPUT);
        assertEquals(parser.getNumberOfSyntaxErrors(), 1);
    }

    @Test
    public void simpleExpression() {
        final String INPUT = "5 + 4";
        assertEquals("(5 + 4)", createParser(INPUT).pp());
    }

    @Test
    public void simpleExpression2() {
        final String INPUT = "0 / 147";
        assertEquals("(0 / 147)", createParser(INPUT).pp());
    }

    @Test
    public void simpleExpression3() {
        final String INPUT = "-5";
        createParser(INPUT);
        assertEquals(1, parser.getNumberOfSyntaxErrors());
    }

    @Test
    public void complexExpression() {
        final String INPUT = "0 / 147 + 7";
        assertEquals("((0 / 147) + 7)", createParser(INPUT).pp());
    }

    @Test
    public void complexExpression4() {
        final String INPUT = "0 + 147 / 7";
        assertEquals("(0 + (147 / 7))", createParser(INPUT).pp());
    }

    @Test
    public void complexExpression5() {
        final String INPUT = "0 + +";
        createParser(INPUT);
        assertEquals(parser.getNumberOfSyntaxErrors(), 1);
    }

    @Test
    public void complexExpression6() {
        final String INPUT = "(0 + 147) / 7";
        assertEquals("((0 + 147) / 7)", createParser(INPUT).pp());
    }

    @Test
    public void complexExpression7() {
        final String INPUT = "(0 + 147 / 7";
        createParser(INPUT);
        assertEquals(parser.getNumberOfSyntaxErrors(), 1);
    }

    @Test
    public void complexExpression8() {
        final String INPUT = "0 + 147 - 7";
        assertEquals("((0 + 147) - 7)", createParser(INPUT).pp());
    }
}

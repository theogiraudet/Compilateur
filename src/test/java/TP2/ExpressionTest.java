package TP2;


import TP2.asd.Program;
import TP2.asd.expression.Expression;
import TP2.utils.Utils;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ExpressionTest {

    private VSLParser parser;

    public Expression createParser(String input) throws RecognitionException {
        VSLLexer lexer = new VSLLexer(CharStreams.fromString(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        parser = new VSLParser(tokens);

        return parser.expression().out;
    }

    private Program createParserFromProgram(String input) throws RecognitionException {
        VSLLexer lexer = new VSLLexer(CharStreams.fromString(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        parser = new VSLParser(tokens);

        SymbolTable.reset();
        Utils.reset();
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
        createParser(INPUT);
        assertEquals(parser.getNumberOfSyntaxErrors(), 2);
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
        assertEquals(parser.getNumberOfSyntaxErrors(), 2);
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
    public void complexExpression8() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/Exp/exp1.vsl");
        Program p = createParserFromProgram(vsl);
        assertThrows(TypeException.class, p::toIR);
    }

    @Test
    public void complexExpression9() {
        final String INPUT = "0 + 147 - 7";
        assertEquals("((0 + 147) - 7)", createParser(INPUT).pp());
    }
}

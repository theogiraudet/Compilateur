package TP2;

import TP2.asd.Program;
import TP2.asd.expression.Expression;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AssignementTest {

    private VSLParser parser;

    private Program createParser(String input) throws RecognitionException {
        VSLLexer lexer = new VSLLexer(CharStreams.fromString(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        parser = new VSLParser(tokens);

        SymbolTable.reset();
        return parser.program().out;
    }

    @BeforeEach
    public void reset() {
        SymbolTable.reset();
    }

    @DisplayName("Affectation à une variable non déclarée")
    @Test
    public void assignement1() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/testAffect0F.vsl");
        createParser(vsl);
        assertEquals(1, parser.getNumberOfSyntaxErrors());
    }

    @DisplayName("Affectation correcte")
    @Test
    public void assignement2() throws IOException {
        final String vsl = UtilsFile.getFileContent("testsPersos/testAffect1V.vsl");
        final String result = "%1.y = alloca i32\n" +
                              "%tmp1 = add i32 5, 4\n" +
                              "store i32 %tmp1, i32* %1.y";
        Program prog = createParser(vsl);
        assertTrue(prog.toIR().toString().contains(result));
    }
}
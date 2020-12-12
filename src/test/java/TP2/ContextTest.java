package TP2;

import TP2.asd.type.Int;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class ContextTest {

  @Test
  public void testLookupEmpty() {
    Context table = new Context();

    assertFalse(table.lookupSymbol("unknown").isPresent());
  }

  @Test
  public void testSimple() {
    Context table = new Context();
    Context.Symbol sym = new Context.VariableSymbol(new Int(), "key");

    assertTrue(table.addSymbol(sym));

    assertFalse(table.lookupSymbol("unknown").isPresent());
    System.out.println(sym.getIdent());
    assertTrue(table.lookupSymbol(sym.getIdent()).isPresent());
    assertEquals(table.lookupSymbol(sym.getIdent()).get(), sym);

    assertTrue(table.removeSymbol(sym.getIdent()));
    assertFalse(table.removeSymbol(sym.getIdent()));

    assertFalse(table.lookupSymbol(sym.getIdent()).isPresent());
  }

  @Test
  public void testParent() {
    Context parent = new Context();
    Context.Symbol sym = new Context.VariableSymbol(new Int(), "key");

    assertTrue(parent.addSymbol(sym));

    Context table = new Context(parent);
    Context.Symbol sym2 = new Context.VariableSymbol(new Int(), "key2");

    assertTrue(table.addSymbol(sym2));

    assertEquals(table.lookupSymbol(sym2.getIdent()).get(), sym2);
    assertEquals(table.lookupSymbol(sym.getIdent()).get(), sym); // in parent

    assertFalse(table.removeSymbol(sym.getIdent())); // in parent
    assertTrue(table.removeSymbol(sym2.getIdent()));
  }

  @Test
  public void testEquals() {
    Context table = new Context();
    Context table2 = new Context();
    Context.Symbol sym = new Context.VariableSymbol(new Int(), "key");
    Context.Symbol sym2 = new Context.VariableSymbol(new Int(), "key2");

    assertNotEquals(sym, sym2);
    assertEquals(table, table2);

    assertTrue(table.addSymbol(sym));
    assertTrue(table2.addSymbol(sym2));

    assertNotEquals(table, table2);

    assertTrue(table2.addSymbol(sym));
    assertTrue(table.addSymbol(sym2));

    assertEquals(table, table2);

    ArrayList<Context.VariableSymbol> arguments = new ArrayList<Context.VariableSymbol>();
    Context.VariableSymbol arg0 = new Context.VariableSymbol(new Int(), "arg0");
    Context.VariableSymbol arg1 = new Context.VariableSymbol(new Int(), "arg1");
    arguments.add(0, arg0);
    arguments.add(0, arg1);

    Context.Symbol fun = new Context.FunctionSymbol(new Int(), "fun", arguments, true);
    Context.Symbol fun2 = new Context.FunctionSymbol(new Int(), "fun2", new ArrayList<Context.VariableSymbol>(), true);

    assertNotEquals(fun, fun2);
    assertTrue(table2.addSymbol(fun));
    assertNotEquals(table, table2);
  }
}

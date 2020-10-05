package TP2;

import java.util.ArrayList;
import junit.framework.TestCase;
import org.junit.*;

import TP2.ASD.Int;

import static org.junit.Assert.assertNotEquals;


public class SymbolTableTest extends TestCase {
  @Test
  public static void testLookupEmpty() {
    SymbolTable table = new SymbolTable();

    assertNull(table.lookup("unknown"));
  }

  @Test
  public static void testSimple() {
    SymbolTable table = new SymbolTable();
    SymbolTable.Symbol sym = new SymbolTable.VariableSymbol(new Int(), "key");

    assertTrue(table.add(sym));

    assertNull(table.lookup("unknown"));
    assertNotNull(table.lookup(sym.ident));
    assertEquals(table.lookup(sym.ident), sym);

    assertTrue(table.remove(sym.ident));
    assertFalse(table.remove(sym.ident));

    assertNull(table.lookup(sym.ident));
  }

  @Test
  public static void testParent() {
    SymbolTable parent = new SymbolTable();
    SymbolTable.Symbol sym = new SymbolTable.VariableSymbol(new Int(), "key");

    assertTrue(parent.add(sym));

    SymbolTable table = new SymbolTable(parent);
    SymbolTable.Symbol sym2 = new SymbolTable.VariableSymbol(new Int(), "key2");

    assertTrue(table.add(sym2));

    assertEquals(table.lookup(sym2.ident), sym2);
    assertEquals(table.lookup(sym.ident), sym); // in parent

    assertFalse(table.remove(sym.ident)); // in parent
    assertTrue(table.remove(sym2.ident));
  }

  @Test
  public static void testEquals() {
    SymbolTable table = new SymbolTable();
    SymbolTable table2 = new SymbolTable();
    SymbolTable.Symbol sym = new SymbolTable.VariableSymbol(new Int(), "key");
    SymbolTable.Symbol sym2 = new SymbolTable.VariableSymbol(new Int(), "key2");

    assertNotEquals(sym, sym2);
    assertEquals(table, table2);

    assertTrue(table.add(sym));
    assertTrue(table2.add(sym2));

    assertNotEquals(table, table2);

    assertTrue(table2.add(sym));
    assertTrue(table.add(sym2));

    assertEquals(table, table2);

    ArrayList<SymbolTable.VariableSymbol> arguments = new ArrayList<SymbolTable.VariableSymbol>();
    SymbolTable.VariableSymbol arg0 = new SymbolTable.VariableSymbol(new Int(), "arg0");
    SymbolTable.VariableSymbol arg1 = new SymbolTable.VariableSymbol(new Int(), "arg1");
    arguments.add(0, arg0);
    arguments.add(0, arg1);

    SymbolTable.Symbol fun = new SymbolTable.FunctionSymbol(new Int(), "fun", arguments, true);
    SymbolTable.Symbol fun2 = new SymbolTable.FunctionSymbol(new Int(), "fun2", new ArrayList<SymbolTable.VariableSymbol>(), true);

    assertNotEquals(fun, fun2);
    assertTrue(table2.add(fun));
    assertNotEquals(table, table2);
  }
}

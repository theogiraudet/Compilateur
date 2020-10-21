package TP2;

import TP2.asd.type.Int;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class SymbolTableTest {

  @Test
  public void testLookupEmpty() {
    SymbolTable table = new SymbolTable();

    assertFalse(table.lookup("unknown").isPresent());
  }

  @Test
  public void testSimple() {
    SymbolTable table = new SymbolTable();
    SymbolTable.Symbol sym = new SymbolTable.VariableSymbol(new Int(), "key");

    assertTrue(table.add(sym));

    assertFalse(table.lookup("unknown").isPresent());
    assertTrue(table.lookup(sym.getIdent()).isPresent());
    assertEquals(table.lookup(sym.getIdent()).get(), sym);

    assertTrue(table.remove(sym.getIdent()));
    assertFalse(table.remove(sym.getIdent()));

    assertFalse(table.lookup(sym.getIdent()).isPresent());
  }

  @Test
  public void testParent() {
    SymbolTable parent = new SymbolTable();
    SymbolTable.Symbol sym = new SymbolTable.VariableSymbol(new Int(), "key");

    assertTrue(parent.add(sym));

    SymbolTable table = new SymbolTable(parent);
    SymbolTable.Symbol sym2 = new SymbolTable.VariableSymbol(new Int(), "key2");

    assertTrue(table.add(sym2));

    assertEquals(table.lookup(sym2.getIdent()).get(), sym2);
    assertEquals(table.lookup(sym.getIdent()).get(), sym); // in parent

    assertFalse(table.remove(sym.getIdent())); // in parent
    assertTrue(table.remove(sym2.getIdent()));
  }

  @Test
  public void testEquals() {
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

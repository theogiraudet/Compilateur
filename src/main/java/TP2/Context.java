package TP2;

import TP2.asd.type.Type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Représente le contexte d'exécution du code
 * Le contexte contient une table des symboles correspondant à un ensemble de couple ident vers symbole correspondant.
 * Un contexte peut avoir un contexte parent. Dans le cas de la recherche d'un symbole, si le contexte
 * courant ne possède aucun symbole de l'ident spécifié, la requête est remontée au contexte parent.
 */
public class Context {
  // Define different symbols

  // Définition des symboles
  public static abstract class Symbol {
    private final String ident; // minimum, used in the storage map

    protected Symbol(String ident) {
      this.ident = ident;
    }

    public String getIdent() {
      return ident;
    }

    @Override
    public String toString() {
      return getIdent();
    }
  }

  public static class VariableParamSymbol extends VariableSymbol {

    public VariableParamSymbol(Type type, String ident) {
      super(type, ident);
    }

    @Override
    public String toString() {
      return "%bp" + super.toString().substring(2);
    }

    @Override
    public boolean equals(Object obj) {
      return obj instanceof VariableParamSymbol && super.equals(obj);
    }
  }

  public static class VariableSymbol extends Symbol {
    private final Type type;
    private int block;

    public VariableSymbol(Type type, String ident) {
      super(ident);
      this.type = type;
    }

    public Type getType() {
      return type;
    }

    @Override
    public String toString() {
      return "%b" + block + "." + getIdent();
    }

    @Override public boolean equals(Object obj) {
      if(obj == null) return false;
      if(obj == this) return true;
      if(!(obj instanceof VariableSymbol)) return false;
      VariableSymbol o = (VariableSymbol) obj;
      return o.type.equals(this.type) &&
        o.getIdent().equals(this.getIdent());
    }
  }

  public static class FunctionSymbol extends Symbol {
    private final Type returnType;
    private final List<VariableSymbol> arguments; // arguments is an ordered list of VariableSymbol
    private final boolean defined; // false if declared but not defined

    public FunctionSymbol(Type returnType, String ident, List<VariableSymbol> arguments, boolean defined) {
      super(ident);
      this.returnType = returnType;
      this.arguments = arguments;
      this.defined = defined;
    }

    public Type getReturnType() {
      return returnType;
    }

    public List<VariableSymbol> getArguments() {
      return arguments;
    }

    public boolean isDefined() {
      return defined;
    }

    public boolean isEqualIgnoreDefine(Object obj) {
      if(obj == null) return false;
      if(obj == this) return true;
      if(!(obj instanceof FunctionSymbol)) return false;
      FunctionSymbol o = (FunctionSymbol) obj;
      return o.returnType.equals(this.returnType) &&
              o.getIdent().equals(this.getIdent()) &&
              o.arguments.equals(this.arguments);
    }

    @Override public boolean equals(Object obj) {
       return isEqualIgnoreDefine(obj) && ((FunctionSymbol) obj).defined == this.defined;
    }
  }

  // Store the table as a map
  private Map<String, Symbol> table;

  // Contexte parent
  private final Context parent;

  // Block ID
  private final int blockId;

  private final FunctionSymbol function;

  /**
   * Crée un nouveau contexte
   */
  public Context() {
    this(null);
  }

  /**
   * Crée un nouveau contexte dont le contexte passé en paramètre est le contexte parent du contexte courant
   * @param parent un contexte parent
   */
  public Context(Context parent) {
    this(parent, null);
  }

  /**
   * Crée un nouveau contexte dont le contexte passé en paramètre est le contexte parent du contexte courant
   * Associe une fonction à ce contexte
   * @param parent un contexte parent
   * @param associedFunc un symbole de fonction à associer au contexte courant
   */
  public Context(Context parent, FunctionSymbol associedFunc) {
    this.table = new HashMap<>();
    this.parent = parent;
    this.function = associedFunc;
    blockId = parent != null ? parent.blockId + 1 : 0;
  }

  // Add a new symbol
  // Returns false if the symbol cannot be added (already in the scope)
  public boolean addSymbol(Symbol sym) {
    final Symbol res = this.table.get(sym.ident);
    if(res != null)
      return false;

    if(sym instanceof VariableSymbol)
      ((VariableSymbol)sym).block = blockId;

    this.table.put(sym.ident, sym);
    return true;
  }

  // Remove a symbol
  // Returns false if the symbol is not in the table (without looking at parent's)
  public boolean removeSymbol(String ident) {
    return this.table.remove(ident) != null;
  }

  public Optional<Symbol> lookupSymbol(String ident) {
    Optional<Symbol> res = Optional.ofNullable(this.table.get(ident));

    if(!res.isPresent() && (this.parent != null)) {
      // Forward request
      return this.parent.lookupSymbol(ident);
    }

    return res; // Either the symbol or null
  }

  public Optional<FunctionSymbol> getAssociedFunction() {
    if (function == null && parent != null)
      return parent.getAssociedFunction();
    return Optional.ofNullable(function);
  }

  @Override public boolean equals(Object obj) {
    if(obj == null) return false;
    if(obj == this) return true;
    if(!(obj instanceof Context)) return false;
    Context o = (Context) obj;
    return o.table.equals(this.table) &&
      ((o.parent == null && this.parent == null) || o.parent.equals(this.parent));
  }
}

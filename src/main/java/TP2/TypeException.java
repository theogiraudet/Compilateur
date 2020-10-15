package TP2;

import java.util.function.Supplier;

public class TypeException extends RuntimeException {
  public TypeException(String message) {
    super(message);
  }

  public TypeException(String message, Supplier<String> at) {
    super(message + "\nat '" + at.get() + "'.");
  }
}

package TP2;

import java.util.function.Supplier;

public class IllegalFormatException extends RuntimeException {

  public IllegalFormatException(String message, Supplier<String> at) {
    super(message + "\nat '" + at.get() + "'.");
  }

}

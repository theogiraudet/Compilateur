package TP2;

import java.nio.CharBuffer;
import java.util.stream.Collectors;

public class Test {

    public static void main(String... args) throws Exception {
        final String test = "\n\t";
        final String test2 = CharBuffer.wrap(test.toCharArray()).chars()
                .mapToObj(c -> c < 32 ? "\\u" + Integer.toHexString(c) : Character.toString((char) c))
                .collect(Collectors.joining());
        System.out.println(test2);
    }
}

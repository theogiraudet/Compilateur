package TP2;

import TP2.utils.Try;

public class Test {

    public static void main(String... args) throws Exception {

        Try<Try<Integer>> test3 = Try.tryThis(() -> Try.tryThis(() -> dangerousOperation()));
        Try<Integer> test4 = test3.flatMap(i -> i);

    }

    public static Integer dangerousOperation() throws RuntimeException {
        return 3 / 1;
    }

}

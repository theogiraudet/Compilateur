package TP2.utils;

public class Tuple {

    public static <K, V> Tuple2<K, V> of(K _1, V _2) {
        return new Tuple2<>(_1, _2);
    }

    public static <K, V, U> Tuple3<K, V, U> of(K _1, V _2, U _3) {
        return new Tuple3<>(_1, _2, _3);
    }

}

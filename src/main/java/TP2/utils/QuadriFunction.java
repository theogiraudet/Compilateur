package TP2.utils;

@FunctionalInterface
public interface QuadriFunction<A, B, C, D, E> {

    E apply(A a, B b, C c, D d);

}

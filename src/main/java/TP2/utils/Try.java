package TP2.utils;

import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class Try<T> {

    /**
     *
     * @param defaultValue une valeur par défaut
     * @return la valeur du Try si Success, la valeur par défaut sinon
     */
    public abstract T getOrDefault(T defaultValue);

    /**
     * @return la valeur du Try si Success, déclenche l'exception encapsulée sinon
     * @throws RuntimeException une exception encapsulée
     */
    public abstract T get() throws RuntimeException;

    /**
     * @param function une fonction à appliquer sur la valeur encapsulée
     * @param <U>
     * @return Un nouveau Try avec pour valeur l'application de la fonction sur la valeur encapsulée si Success, le même Try sinon
     */
    public abstract <U> Try<U> map(Function<T, U> function);

    /**
     * @param element un Try<T>
     * @param function une fonction de réduction du Try courant par l'élément passé en paramètre
     * @return un nouveau Try résultant de l'application de function sur la valeur courante et l'élément passé en paramètre si Success.
     * Si l'élément courant ou celui passé en paramètre est un Failure, retourne l'élément en question
     */
    public abstract Try<T> reduce(Try<T> element, BiFunction<T, T, T> function);

    /**
     * @param mapper une fonction à appliquer sur la valeur encapsulée
     * @param <U>
     * @return Un nouveau Try avec pour valeur l'application de la fonction sur la valeur encapsulée si Success, le même Try sinon
     * Si Try<Try<T>>, ne renvoie que le Try<T></T>
     */
    public abstract <U> Try<U> flatMap(Function<? super T, Try<U>> mapper);

    /**
     * @return Vrai si l'application de la fonction n'a pas renvoyé d'erreur, faux sinon
     */
    public boolean succeed() {
        return this instanceof Success;
    }

    /**
     * @return Vrai si l'application de la fonction a renvoyé d'erreur, faux sinon
     */
    public boolean failed() {
        return !succeed();
    }

    /**
     * @param opt un Optional à convertir
     * @param <T>
     * @return un Success encapsulant la valeur du Optional si celui-ci n'est pas vide, un Failure encapsulant un NullPointerException sinon
     */
    public static <T> Try<T> toTry(Optional<T> opt) {
        if(opt.isPresent())
            return new Success<>(opt.get());
        else
            return new Failure<>(new NullPointerException());
    }

    /**
     * @param fun un Supplier
     * @param <T>
     * @return Un Success encapsulant la valeur retournée par fun si celui-ci n'a pas renvoyé d'erreur, un Failure encapsulant l'erreur renvoyée sinon
     */
    public static <T> Try<T> tryThis(Supplier<T> fun) {
        try {
            T result = fun.get();
            return new Success<>(result);
        } catch(RuntimeException e) {
            return new Failure<>(e);
        }
    }

    private static class Success<T> extends Try<T> {

        private final T value;

        private Success(T value) {
            this.value = value;
        }

        @Override
        public T getOrDefault(T defaultValue) {
            return get();
        }

        @Override
        public T get() {
            return value;
        }

        @Override
        public <U> Try<U> map(Function<T, U> function) {
            return Try.tryThis(() -> function.apply(value));
        }

        @Override
        public Try<T> reduce(Try<T> element, BiFunction<T, T, T> function) {
            return element.succeed()
                    ? Try.tryThis(() -> function.apply(get(), element.getOrDefault(null)))
                    : element;
        }

        public <U> Try<U> flatMap(Function<? super T, Try<U>> mapper) {
            Objects.requireNonNull(mapper);
            return Objects.requireNonNull(mapper.apply(value));
        }
    }


    private static class Failure<T> extends Try<T> {

        private final RuntimeException exception;

        public Failure(RuntimeException exception) {
            this.exception = exception;
        }

        @Override
        public T getOrDefault(T defaultValue) {
            return defaultValue;
        }

        @Override
        public T get() throws RuntimeException {
            throw exception;
        }

        @Override
        public <U> Try<U> map(Function<T, U> function) {
            return new Failure<>(exception);
        }

        @Override
        public Try<T> reduce(Try<T> element, BiFunction<T, T, T> function) {
            return this;
        }

        public <U> Try<U> flatMap(Function<? super T, Try<U>> mapper) {
            return new Failure<>(exception);
        }
    }
}

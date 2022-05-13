package exceptions;

@FunctionalInterface
public interface FunctionException<T, R, E extends Throwable> {
        R apply(T t) throws E;
}

package exceptions;

import java.io.UnsupportedEncodingException;
import java.util.function.Consumer;

@FunctionalInterface
public interface ConsumerException<T,Q, E extends AlgorithmException> {
    void accept(T t, Q q) throws E, UnsupportedEncodingException;
}

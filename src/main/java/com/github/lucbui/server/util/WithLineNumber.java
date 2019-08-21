package com.github.lucbui.server.util;

import java.util.function.Function;

/**
 * Function which pairs the incoming value with the line number
 * Internally, a counter keeps track of the amount of times apply() is called. The input is paired with this counter's
 * value, and the counter incremented, on each apply().
 * @param <T> The incoming value
 */
public class WithLineNumber<T> implements Function<T, Pair<Integer, T>> {
    private int ctr;

    /**
     * Initializes this stateful function
     */
    public WithLineNumber() {
        ctr = 0;
    }

    @Override
    public Pair<Integer, T> apply(T t) {
        return new Pair<>(ctr++, t);
    }
}

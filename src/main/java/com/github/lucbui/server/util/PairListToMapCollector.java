package com.github.lucbui.server.util;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * A collector which converts a list of Pairs into a Map
 * @param <K> The key type
 * @param <V> The value type
 */
public class PairListToMapCollector<K, V> implements Collector<Pair<K, V>, Map<K, V>, Map<K, V>> {
    @Override
    public Supplier<Map<K, V>> supplier() {
        return HashMap::new;
    }

    @Override
    public BiConsumer<Map<K, V>, Pair<K, V>> accumulator() {
        return (map, pair) -> map.put(pair.getKey(), pair.getValue());
    }

    @Override
    public BinaryOperator<Map<K, V>> combiner() {
        return (a, b) -> {a.putAll(b); return a;};
    }

    @Override
    public Function<Map<K, V>, Map<K, V>> finisher() {
        return Function.identity();
    }

    @Override
    public Set<Characteristics> characteristics() {
        return EnumSet.of(Characteristics.IDENTITY_FINISH, Characteristics.UNORDERED);
    }
}

package com.github.lucbui.util;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class ListToOptionalCollector<T> implements Collector<T, ListToOptionalCollector.MutableObject<T>, Optional<T>> {

    @Override
    public Supplier<MutableObject<T>> supplier() {
        return MutableObject::new;
    }

    @Override
    public BiConsumer<MutableObject<T>, T> accumulator() {
        return (cont, obj) -> {
            if(cont.object != null){
                throw new IllegalStateException("Stream must have zero or one elements");
            }
            cont.object = obj;
        };
    }

    @Override
    public BinaryOperator<MutableObject<T>> combiner() {
        return (cont1, cont2) -> {
            if(cont1.object != null && cont2.object != null){
                throw new IllegalStateException("Stream must have zero or one elements");
            } else if(cont1.object == null && cont2.object != null){
                cont1.object = cont2.object;
            }
            return cont1;
        };
    }

    @Override
    public Function<MutableObject<T>, Optional<T>> finisher() {
        return in -> Optional.ofNullable(in.object);
    }

    @Override
    public Set<Characteristics> characteristics() {
        return EnumSet.of(Characteristics.UNORDERED);
    }

    static class MutableObject<T> {
        T object;
    }
}

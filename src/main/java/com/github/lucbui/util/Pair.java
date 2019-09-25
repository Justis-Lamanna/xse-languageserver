package com.github.lucbui.util;

/**
 * Standard class which pairs two objects together.
 * Sure, it exists in Apache Commons, but that's no fun. Plus it's dead easy to make anyway.
 * @param <K> The key type
 * @param <V> The value type
 */
public class Pair<K, V>{
    private K key;
    private V value;

    /**
     * Create a pair
     * @param key Key object
     * @param value Value object
     */
    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Get the key
     * @return The key
     */
    public K getKey() {
        return key;
    }

    /**
     * Get the value
     * @return The value
     */
    public V getValue() {
        return value;
    }
}

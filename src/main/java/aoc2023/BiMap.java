package aoc2023;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * A bidirectional "map".
 * Because this is just a "map" of 2 lists, users should be careful when having duplicate values, as unlike normal maps,
 * this implementation does not replace key/values, but append them only.
 * The getters would only be returning the first instance of the parameter passed through,
 * so one should make sure that one of the parameters, key or value, should never repeat.
 */
public class BiMap<K, V> {
    private final List<K> k;
    private final List<V> v;

    public BiMap() {
        this.k = new ArrayList<>();
        this.v = new ArrayList<>();
    }

    public BiMap(BiMap<? extends K, ? extends V> map) {
        this.k = new ArrayList<>(map.k);
        this.v = new ArrayList<>(map.v);
    }

    public void put(K key, V value) {
        this.k.add(key);
        this.v.add(value);
    }

    public V getValue(K key) {
        return this.v.get(this.k.indexOf(key));
    }

    public K getKey(V value) {
        return this.k.get(this.v.indexOf(value));
    }

    public List<K> keyList() {
        return new ArrayList<>(this.k);
    }

    public List<V> valueList() {
        return new ArrayList<>(this.v);
    }

    public int size() {
        return this.k.size();
    }

    public boolean isEmpty() {
        return this.k.isEmpty();
    }

    public boolean containsKey(K k) {
        return this.k.contains(k);
    }

    public boolean containsValue(V v) {
        return this.v.contains(v);
    }

    public void removeKey(K k) {
        this.v.remove(this.k.indexOf(k));
        this.k.remove(k);
    }

    public void removeValue(V v) {
        this.k.remove(this.v.indexOf(v));
        this.v.remove(v);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BiMap<?, ?> biMap)) return false;
        return this.k.equals(biMap.k) && this.v.equals(biMap.v);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.k, this.v);
    }

    @Override
    public String toString() {
        return "BiMap{" +
                IntStream.range(0, this.size()).mapToObj(i -> k.get(i) + "<->" + v.get(i)).collect(Collectors.joining(", "))
                + "}";
    }
}

package aoc2023;

import java.util.*;

public class Utility {

    public static <K, V> List<K> getKeysByValue(Map<K, V> map, V value) {
        return map.entrySet().stream()
                  .filter(entry -> entry.getValue().equals(value))
                  .map(Map.Entry::getKey).toList();
    }
}

package AoC2023.Day1;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class NumberStringParser {
    private final List<String> lines = new ArrayList<>();

    public NumberStringParser(List<String> lines) {
        this.lines.addAll(lines);
    }

    public Integer getSum() {
        return parse().stream().mapToInt(Integer::intValue).sum();
    }

    private List<Integer> parse() {
        return lines.stream().map(this::parseLine).toList();
    }

    private Integer parseLine(String line) {
        // Get all the things for matching
        List<String> patternStrings = List.of(
                "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine",
                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9");

        List<Matcher> matchers = patternStrings.stream().map(Pattern::compile).map(i -> i.matcher(line)).toList();

        // Map from word to value
        Map<String, Integer> wordToValue = new HashMap<>();
        IntStream.range(0, 10).forEach(i -> wordToValue.put(patternStrings.get(i), i));

        // Map of the digit's index to its value
        Map<Integer, Integer> indexToValueFront = new HashMap<>();
        Map<Integer, Integer> indexToValueBack = new HashMap<>();


        matchers.forEach(i -> {
            while (i.find()) {
                int startIndex = i.start();
                String s = line.substring(startIndex, i.end());

                Integer value = s.length() == 1 ? Integer.parseInt(s) : wordToValue.get(s);

                // If the value doesn't exist, or if the starting index is less than what is stored
                // Short-circuiting happens here
                if (!indexToValueFront.containsValue(value) || startIndex < getKeysByValue(indexToValueFront, value))
                    indexToValueFront.put(startIndex, value);
                // This always replaces as we want the value that's at the back
                indexToValueBack.put(startIndex, value);
            }
        });

        return indexToValueFront.get(indexToValueFront.keySet().stream().sorted().toList().get(0)) * 10 +
                indexToValueBack.get(indexToValueBack.keySet().stream().sorted().toList().get(indexToValueBack.size() - 1));
    }

    private static <T, E> T getKeysByValue(Map<T, E> map, E value) {
        return map.entrySet().stream()
                  .filter(entry -> entry.getValue().equals(value))
                  .map(Map.Entry::getKey).toList().get(0);
    }


}

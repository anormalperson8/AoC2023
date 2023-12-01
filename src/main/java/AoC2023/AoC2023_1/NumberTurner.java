package AoC2023.AoC2023_1;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class NumberTurner {
    private final List<String> lines = new ArrayList<>();

    public NumberTurner(List<String> lines) {
        this.lines.addAll(lines);
    }

    public Integer getSum() {
        return parse().stream().mapToInt(Integer::intValue).sum();
    }

    public Integer getSum2() {
        return parse2().stream().mapToInt(Integer::intValue).sum();
    }

    private List<Integer> parse() {
        return lines.stream().map(this::parseLine).toList();
    }

    private Integer parseLine(String line) {
        String temp = line.replaceAll("[^0-9]", "");
        return Integer.parseInt(temp.charAt(0) + "" + temp.charAt(temp.length() - 1));
    }

    private List<Integer> parse2() {
        return lines.stream().map(this::parseLine2).toList();
    }

    private Integer parseLine2(String line) {
        // Get all the things for matching
        List<String> patternStrings = List.of(
                "one", "two", "three", "four", "five", "six", "seven", "eight", "nine",
                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9");

        List<Pattern> patterns = patternStrings.stream().map(Pattern::compile).toList();
        List<Matcher> matchers = patterns.stream().map(i -> i.matcher(line)).toList();

        // Map from word to value
        Map<String, Integer> wordToValue = new HashMap<>();
        IntStream.range(1, 10).forEach(i -> wordToValue.put(patternStrings.get(i - 1), i));

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

        List<Integer> indexes = new ArrayList<>(indexToValueFront.keySet());
        Collections.sort(indexes);
        int first = indexToValueFront.get(indexes.get(0));
        List<Integer> indexes2 = new ArrayList<>(indexToValueBack.keySet());
        Collections.sort(indexes2);
        int second = indexToValueBack.get(indexes2.get(indexes2.size() - 1));

        return first * 10 + second;
    }

    private static <T, E> T getKeysByValue(Map<T, E> map, E value) {
        return map.entrySet().stream()
                  .filter(entry -> entry.getValue().equals(value))
                  .map(Map.Entry::getKey).toList().get(0);
    }

}

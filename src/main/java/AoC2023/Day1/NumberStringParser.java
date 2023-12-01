package AoC2023.Day1;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class NumberStringParser {
    private final List<String> lines = new ArrayList<>();

    // Get all the things for matching
    List<String> patternStrings = List.of(
            "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine",
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9");


    public NumberStringParser(List<String> lines) {
        this.lines.addAll(lines);
    }

    public Integer getSum() {
        return this.lines.stream().map(this::parseLine).mapToInt(Integer::intValue).sum();
    }

    private Integer parseLine(String line) {
        // Create list of matchers
        List<Matcher> matchers = patternStrings.stream().map(Pattern::compile).map(i -> i.matcher(line)).toList();

        // Map from word to value
        Map<String, Integer> wordToValue = new HashMap<>();
        IntStream.range(0, 20).forEach(i -> wordToValue.put(patternStrings.get(i), i % 10));

        // Map of the digit's index to its value
        Map<Integer, Integer> indexToValue = new HashMap<>();

        matchers.forEach(i -> {
            while (i.find())
                indexToValue.put(i.start(), wordToValue.get(line.substring(i.start(), i.end())));
        });

        // Sort the keySet to find the smallest and largest index, then return the sum
        return indexToValue.get(indexToValue.keySet().stream().sorted().toList().get(0)) * 10 +
                indexToValue.get(indexToValue.keySet().stream().sorted().toList().get(indexToValue.size() - 1));
    }
}

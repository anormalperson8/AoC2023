package AoC2023.Day1;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class NumberParser {
    protected final List<String> lines = new ArrayList<>();

    public NumberParser(List<String> lines) {
        this.lines.addAll(lines);
    }

    public Integer getSum() {
        return parse().stream().mapToInt(Integer::intValue).sum();
    }

    private List<Integer> parse() {
        return lines.stream().map(this::parseLine).toList();
    }

    private Integer parseLine(String line) {
        String temp = line.replaceAll("[^0-9]", "");
        return Integer.parseInt(temp.charAt(0) + "" + temp.charAt(temp.length() - 1));
    }

}
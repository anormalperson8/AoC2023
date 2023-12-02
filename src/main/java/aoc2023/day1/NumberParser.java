package aoc2023.day1;

import java.util.*;

public class NumberParser {
    private final List<String> lines = new ArrayList<>();

    public NumberParser(List<String> lines) {
        this.lines.addAll(lines);
    }

    public Integer getSum() {
        return this.lines.stream().map(this::parseLine).mapToInt(Integer::intValue).sum();
    }

    private Integer parseLine(String line) {
        String temp = line.replaceAll("[^0-9]", "");
        return Integer.parseInt(temp.charAt(0) + "" + temp.charAt(temp.length() - 1));
    }

}

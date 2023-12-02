package aoc2023.Day1;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class AlternateNumberStringParser {
    private final List<String> lines = new ArrayList<>();

    // Get all the things for matching
    private final List<String> patternStrings = new ArrayList<>(List.of(
                "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine",
                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"));

    public AlternateNumberStringParser(List<String> lines, boolean b) {
        if (!b) IntStream.range(0,10).forEach(i -> patternStrings.remove(0));
        this.lines.addAll(lines);
    }

    public Integer getSum() {
        return this.lines.stream().map(i -> {
            List<Integer> l = new ArrayList<>();
            parseLine(i, l);
            return l.get(0) * 10 + l.get(l.size() - 1);
        }).mapToInt(Integer::intValue).sum();
    }

    private void parseLine(String line, List<Integer> l) {
        if (line.isEmpty())
            return;

        patternStrings.forEach(i -> {
            if (line.startsWith(i))
                l.add(patternStrings.indexOf(i) % 10);
        });

        parseLine(line.substring(1), l);
    }


}

package aoc2023.Day2;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class CubeGameStringParser {
    private final List<String> lines;
    private static final int RED = 12;
    private static final int GREEN = 13;
    private static final int BLUE = 14;


    public CubeGameStringParser(List<String> lines) {
        this.lines = new ArrayList<>(lines);
    }

    public int get() {
        return this.lines.stream().map(this::parseLine).mapToInt(Integer::intValue).sum();
    }

    private int parseLine(String line) {
        return Stream.of(line.split(": ")[1].split("; "))
                .map(i -> List.of(i.split(", "))).map(i -> i.stream().map(j -> {
                    switch (j.split(" ")[1]) {
                        case "red" -> {
                            return Integer.parseInt(j.split(" ")[0]) <= CubeGameStringParser.RED;
                        }
                        case "green" -> {
                            return Integer.parseInt(j.split(" ")[0]) <= CubeGameStringParser.GREEN;
                        }
                        case "blue" -> {
                            return Integer.parseInt(j.split(" ")[0]) <= CubeGameStringParser.BLUE;
                        }
                        default -> throw new IllegalArgumentException("Invalid colour.");
                    }
                }).reduce(true, (k, l) -> k && l))
                .reduce(true, (k, l) -> k && l) ?
                Integer.parseInt(line.split(": ")[0].replaceAll("Game ", "")) : 0;
    }

    public int parseLines() {
        return this.lines.stream().map(line -> Stream.of(line.split(": ")[1].split("; "))
                .map(i -> List.of(i.split(", "))).map(i -> i.stream().map(j -> {
                    switch (j.split(" ")[1]) {
                        case "red" -> {
                            return Integer.parseInt(j.split(" ")[0]) <= CubeGameStringParser.RED;
                        }
                        case "green" -> {
                            return Integer.parseInt(j.split(" ")[0]) <= CubeGameStringParser.GREEN;
                        }
                        case "blue" -> {
                            return Integer.parseInt(j.split(" ")[0]) <= CubeGameStringParser.BLUE;
                        }
                        default -> throw new IllegalArgumentException("Invalid colour.");
                    }
                }).reduce(true, (k, l) -> k && l))
                .reduce(true, (k, l) -> k && l) ?
                Integer.parseInt(line.split(": ")[0].replaceAll("Game ", "")) : 0).mapToInt(Integer::intValue).sum();
    }
}

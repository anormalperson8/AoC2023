package aoc2023.Day2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class CubeGameStringNumberGuesser {
    private final List<String> lines;

    public CubeGameStringNumberGuesser(List<String> lines) {
        this.lines = new ArrayList<>(lines);
    }

    public int get() {
        return this.lines.stream().map(this::parseLine).mapToInt(Integer::intValue).sum();
    }

    private int parseLine(String line) {
        AtomicInteger red = new AtomicInteger(0);
        AtomicInteger green = new AtomicInteger(0);
        AtomicInteger blue = new AtomicInteger(0);
        Stream.of(line.split(": ")[1].split("; "))
                .map(i -> List.of(i.split(", "))).forEach(i -> i.forEach(j -> {
                    switch (j.split(" ")[1]) {
                        case "red" -> red.set(Math.max(Integer.parseInt(j.split(" ")[0]), red.get()));
                        case "green" -> green.set(Math.max(Integer.parseInt(j.split(" ")[0]), green.get()));
                        case "blue" -> blue.set(Math.max(Integer.parseInt(j.split(" ")[0]), blue.get()));
                        default -> throw new IllegalArgumentException("Invalid colour.");
                    }
                }));
        return red.get() * green.get() * blue.get();

    }

    public int parseLines() {
        return this.lines.stream().map(line -> {
            AtomicInteger red = new AtomicInteger(0);
            AtomicInteger green = new AtomicInteger(0);
            AtomicInteger blue = new AtomicInteger(0);
            Stream.of(line.split(": ")[1].split("; "))
                    .map(i -> List.of(i.split(", "))).forEach(i -> i.forEach(j -> {
                        switch (j.split(" ")[1]) {
                            case "red" -> red.set(Math.max(Integer.parseInt(j.split(" ")[0]), red.get()));
                            case "green" -> green.set(Math.max(Integer.parseInt(j.split(" ")[0]), green.get()));
                            case "blue" -> blue.set(Math.max(Integer.parseInt(j.split(" ")[0]), blue.get()));
                            default -> throw new IllegalArgumentException("Invalid colour.");
                        }
                    }));
            return red.get() * green.get() * blue.get();
        }).mapToInt(Integer::intValue).sum();
    }


}

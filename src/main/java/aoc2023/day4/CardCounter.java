package aoc2023.day4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CardCounter {
    private final List<List<Integer>> winningNumbers = new ArrayList<>();
    private final List<List<Integer>> cardNumbers = new ArrayList<>();
    private final List<Integer> count;
    private final List<Integer> num;

    public CardCounter(List<String> lines) {
        lines.stream().map(l -> l.substring(10)).map(l -> new ArrayList<>(List.of(l.split(" \\| "))))
                .forEach(l -> {
                    winningNumbers.add(new ArrayList<>(Arrays.stream(l.get(0).split(" ")).filter(i -> !i.isEmpty()).map(Integer::parseInt).toList()));
                    cardNumbers.add(new ArrayList<>(Stream.of(l.get(1).split(" ")).filter(i -> !i.isEmpty()).map(Integer::parseInt).toList()));
                });

        num = new ArrayList<>(IntStream.range(0, winningNumbers.size()).map(i -> 1).boxed().toList());

        count = new ArrayList<>(IntStream.range(0, winningNumbers.size()).map(
                i -> cardNumbers.get(i).stream().filter(j -> winningNumbers.get(i).contains(j)).toList().size()
        ).boxed().toList());
    }

    public int count() {
        IntStream.range(0, winningNumbers.size()).forEach(
                i -> IntStream.range(0, count.get(i)).forEach(j -> num.set(i + j + 1, num.get(i + j + 1) + num.get(i)))
        );

        return num.stream().mapToInt(Integer::intValue).sum();
    }

}

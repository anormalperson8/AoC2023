package aoc2023.day4;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class CardMatcher {
    private final List<List<Integer>> winningNumbers = new ArrayList<>();
    private final List<List<Integer>> cardNumbers = new ArrayList<>();

    public CardMatcher(List<String> lines) {
        lines.stream().map(l -> l.substring(10)).map(l -> new ArrayList<>(List.of(l.split(" \\| "))))
                .forEach(l -> {
                    winningNumbers.add(new ArrayList<>(Stream.of(l.get(0).split(" "))
                            .filter(i -> !i.isEmpty()).map(Integer::parseInt).toList()));
                    cardNumbers.add(new ArrayList<>(Stream.of(l.get(1).split(" "))
                            .filter(i -> !i.isEmpty()).map(Integer::parseInt).toList()));
                });
    }

    public int sum() {
        return cardNumbers.stream().map(l -> Math.pow(2, l.stream().filter(i ->
                winningNumbers.get(cardNumbers.indexOf(l)).contains(i)
        ).toList().size() - 1)).map(Math::floor).map(Math::round).mapToInt(Long::intValue).sum();
    }
}

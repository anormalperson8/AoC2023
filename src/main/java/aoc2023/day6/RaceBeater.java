package aoc2023.day6;

import aoc2023.Pair;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class RaceBeater {

    private final List<Pair<Integer, Integer>> races = new ArrayList<>();
    private final Pair<Long, Long> realRace;

    public RaceBeater(List<String> lines){
        List<Integer> list = lines.stream().map(l -> Arrays.stream(l.split(" "))
                        .filter(i -> !i.isEmpty()).toList().subList(1, 5).stream().map(Integer::parseInt).toList())
                .flatMap(Collection::stream).toList();

        IntStream.range(0, list.size() / 2).forEach(i -> races.add(new Pair<>(list.get(i), list.get(list.size() / 2 + i))));

        List<String> temp = lines.stream().map(l -> Arrays.stream(l.split(" "))
                        .filter(i -> !i.isEmpty()).toList().subList(1, 5).stream().reduce((i, j) -> i + j).orElseThrow()).toList();

        realRace = new Pair<>(Long.parseLong(temp.get(0)), Long.parseLong(temp.get(1)));

    }

    public int win() {
        return races.stream().map(r ->
                IntStream.rangeClosed(0, r.key()).map(i ->
                        i * (r.key() - i)
                ).boxed().filter(i -> i > r.value()).toList().size()
        ).reduce((i, j) -> i * j).orElseThrow();
    }

    public int bigWin() {
        return LongStream.rangeClosed(0, realRace.key()).map(i ->
                        i * (realRace.key() - i)
                ).boxed().filter(i -> i > realRace.value()).toList().size();
    }
}

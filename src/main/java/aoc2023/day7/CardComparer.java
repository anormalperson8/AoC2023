package aoc2023.day7;

import aoc2023.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.LongStream;

public class CardComparer {
    private final List<Pair<CardDeck, Integer>> cardList;
    private final List<Pair<CardDeckWithJoker, Integer>> cardWithJokerList;
    public CardComparer(List<String> lines) {
        List<String[]> t = lines.stream().map(l -> l.split(" ")).toList();

        cardList = new ArrayList<>(t.stream().map(l -> new Pair<>(new CardDeck(l[0]), Integer.parseInt(l[1]))).toList());
        cardList.sort(Comparator.comparing(Pair::key));

        cardWithJokerList = new ArrayList<>(t.stream().map(l -> new Pair<>(new CardDeckWithJoker(l[0]), Integer.parseInt(l[1]))).toList());
        cardWithJokerList.sort(Comparator.comparing(Pair::key));

    }

    public long winnings() {
        return LongStream.range(0, cardList.size()).boxed().map(i -> (i + 1) * cardList.get(i.intValue()).value()).reduce(Long::sum).orElseThrow();
    }

    public long winningsWithJoker() {
        return LongStream.range(0, cardWithJokerList.size()).boxed().map(i -> (i + 1) * cardWithJokerList.get(i.intValue()).value()).reduce(Long::sum).orElseThrow();
    }




}

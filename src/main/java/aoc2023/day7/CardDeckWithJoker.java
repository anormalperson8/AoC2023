package aoc2023.day7;

import java.util.*;

public class CardDeckWithJoker implements Comparable<CardDeckWithJoker> {
    private final List<CardWithJoker> cards;
    private final HashMap<CardWithJoker, Integer> map = new HashMap<>();
    private final List<CardWithJoker> sortedCards;

    public CardDeckWithJoker(String line) {
         cards = Arrays.stream(line.split("")).map(i -> i.charAt(0)).map(CardWithJoker::new).toList();

         HashMap<CardWithJoker, Integer> tempMap = new HashMap<>();
         cards.stream().sorted().forEach(i -> tempMap.put(i, tempMap.containsKey(i) ? tempMap.get(i) + 1 : 1));

         sortedCards = this.touchUp(tempMap).stream().sorted((o1, o2) -> this.map.get(o1).equals(this.map.get(o2)) ?
                 o2.compareTo(o1): -1 * Integer.compare(this.map.get(o1), this.map.get(o2))).toList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CardDeckWithJoker cardDeck)) return false;
        return Objects.equals(cards, cardDeck.cards);
    }

    @Override
    public int compareTo(CardDeckWithJoker cardDeck) {
        // Set smaller means winning
        int self = this.map.size();
        int other = cardDeck.map.size();
        return self == other ?
                switch (self) {
            // 5 of a kind, 1 pair and high card all mean comparing individual card
            case 1, 4, 5 -> compareCards(cardDeck.cards);
            // 4 of a kind OR full house; 3 of a kind OR 2 pairs
            case 2, 3 -> this.map.get(this.sortedCards.get(0)).equals(cardDeck.map.get(cardDeck.sortedCards.get(0))) ?
                    compareCards(cardDeck.cards) :
                    this.map.get(this.sortedCards.get(0)).compareTo(cardDeck.map.get(cardDeck.sortedCards.get(0)));
            default -> throw new IllegalStateException("Unexpected value: Set with " + this.map.size() + " elements.");
        } : -Integer.compare(this.map.size(), cardDeck.map.size());
    }

    private int compareCards(List<CardWithJoker> other) {
        int i = 0;
        while (i < 5) {
            if (!this.cards.get(i).equals(other.get(i)))
                return this.cards.get(i).compareTo(other.get(i));
            ++i;
        }
        return 0;
    }

    private List<CardWithJoker> touchUp(HashMap<CardWithJoker, Integer> tempMap) {
        CardWithJoker joker = new CardWithJoker('J');
        List<CardWithJoker> ret;

        // No J or only J
        if (!tempMap.containsKey(joker) || tempMap.keySet().stream().filter(i -> !i.equals(joker)).toList().isEmpty())
            ret = this.cards;

        else {
            ret = new ArrayList<>();
            // Find the highest non-joker card
            CardWithJoker temp = tempMap.keySet().stream().filter(i -> !i.equals(joker)).reduce((i, j) -> tempMap.get(i) > tempMap.get(j) ? i : j).orElseThrow();

            this.cards.forEach(i -> ret.add(i.equals(joker) ? new CardWithJoker(temp) : i));

            // System.out.println("For cards: " + this.cards + ",\n" +
            // "the card is: " + temp + "\n" +
            // "and now it is: " + ret + "\n" +
            // "and the map size is: " + this.map.size());
        }
        ret.stream().sorted().forEach(i -> this.map.put(i, this.map.containsKey(i) ? this.map.get(i) + 1 : 1));
        return ret;
    }

    @Override
    public String toString() {
        return "CardDeckWithJoker{" + this.cards.stream().map(CardWithJoker::toString).reduce((i, j) -> i + j).orElseThrow() + "}";
    }

    @Override
    public int hashCode() {
        return Objects.hash(cards);
    }

    static class CardWithJoker implements Comparable<CardWithJoker> {
        private final int value;

        public CardWithJoker(Character c) {
            value = Character.isDigit(c) ? Integer.parseInt(c + "") :
            switch (c) {
                case 'J' -> 1;
                case 'T' -> 10;
                case 'Q' -> 12;
                case 'K' -> 13;
                case 'A' -> 14;
                default -> throw new IllegalStateException("Unexpected value: " + c);
            };
        }

        public CardWithJoker(CardWithJoker c) {
            this.value = c.value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof CardWithJoker card)) return false;
            return value == card.value;
        }

        @Override
        public String toString() {
            return "" + (value < 10 && value > 1 ? value : switch (value) {
                case 1 -> "J";
                case 10 -> "T";
                case 12 -> "Q";
                case 13 -> "K";
                case 14 -> "A";
                default -> throw new IllegalStateException("Unexpected value: " + value);
            });
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }

        @Override
        public int compareTo(CardWithJoker card) {
            return Integer.compare(this.value - card.value, 0);
        }
    }
}

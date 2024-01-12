package aoc2023.day7;

import java.util.*;

public class CardDeck implements Comparable<CardDeck> {
    private final List<Card> cards;
    private final HashMap<Card, Integer> map = new HashMap<>();
    private final List<Card> sortedCards;
    public String temp() {return this.cards.toString();}

    public CardDeck(String line) {
         cards = Arrays.stream(line.split("")).map(i -> i.charAt(0)).map(Card::new).toList();
         cards.stream().sorted(Card::compareTo).forEach(i -> map.put(i, map.containsKey(i) ? map.get(i) + 1 : 1));
         sortedCards = cards.stream().sorted((o1, o2) -> map.get(o1).equals(map.get(o2)) ?
                 o2.compareTo(o1): -1 * Integer.compare(map.get(o1), map.get(o2))).toList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CardDeck cardDeck)) return false;
        return Objects.equals(cards, cardDeck.cards);
    }

    @Override
    public int compareTo(CardDeck cardDeck) {
        // Set smaller means winning
        return this.map.size() == cardDeck.map.size() ?
                switch (this.map.size()) {
            // 5 of a kind, 1 pair and high card all mean comparing individual card
            case 1, 4, 5 -> compareCards(cardDeck.cards);
            // 4 of a kind OR full house; 3 of a kind OR 2 pairs
            case 2, 3 -> this.map.get(this.sortedCards.get(0)).equals(cardDeck.map.get(cardDeck.sortedCards.get(0))) ?
                    compareCards(cardDeck.cards) :
                    this.map.get(this.sortedCards.get(0)).compareTo(cardDeck.map.get(cardDeck.sortedCards.get(0)));
            default -> throw new IllegalStateException("Unexpected value: Set with " + this.map.size() + " elements.");
        } : -Integer.compare(this.map.size(), cardDeck.map.size());
    }

    private int compareCards(List<Card> other) {
        int i = 0;
        while (i < 5) {
            if (!this.cards.get(i).equals(other.get(i)))
                return this.cards.get(i).compareTo(other.get(i));
            ++i;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "CardDeck{" + this.cards.stream().map(Card::toString).reduce((i, j) -> i + j).orElseThrow() + "}";
    }

    @Override
    public int hashCode() {
        return Objects.hash(cards);
    }

    static class Card implements Comparable<Card> {
        private final int value;

        public Card(Character c) {
            value = Character.isDigit(c) ? Integer.parseInt(c + "") :
            switch (c) {
                case 'T' -> 10;
                case 'J' -> 11;
                case 'Q' -> 12;
                case 'K' -> 13;
                case 'A' -> 14;
                default -> throw new IllegalStateException("Unexpected value: " + c);
            };
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Card card)) return false;
            return value == card.value;
        }

        @Override
        public String toString() {
            return "" + (value < 10 ? value : switch (value) {
                case 10 -> "T";
                case 11 -> "J";
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
        public int compareTo(Card card) {
            return Integer.compare(this.value - card.value, 0);
        }
    }
}

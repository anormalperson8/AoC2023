package aoc2023.day5;

import aoc2023.Pair;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class LocationFinder {
    private final List<Long> seeds;

    private final List<Pair<Long, Long>> seedRanges = new ArrayList<>();
    private final List<List<List<String>>> words = new ArrayList<>();

    public LocationFinder(List<String> lines) {
        if (lines.isEmpty()) {
            seeds = new ArrayList<>();
            return;
        }

        seeds = new ArrayList<>(Stream.of(lines.get(0).substring(7).split(" ")).map(Long::parseLong).toList());
        List<List<String>> temp = lines.subList(3, lines.size()).stream().map(i -> Stream.of(i.split(" "))).map(Stream::toList).toList();

        AtomicInteger mapNo = new AtomicInteger(0);
        //temp.forEach(System.out::println);

        // Create list for each map
        IntStream.range(0, 7).forEach(i -> words.add(new ArrayList<>()));

        // Separate each map into a list
        temp.stream().filter(l -> l.size() != 1).forEach(l -> {
            if (l.size() != 3) {
                mapNo.incrementAndGet();
                return;
            }
            words.get(mapNo.get()).add(l);
        });

        IntStream.range(0, seeds.size() / 2)
                .forEach(i -> seedRanges.add(new Pair<>(seeds.get(2 * i), seeds.get(2 * i) + seeds.get(2 * i + 1) - 1)));
        seedRanges.sort(Comparator.comparingLong(Pair::key));
    }


    public long findSeed() {
        List<Long> l = seeds.stream().map(this::search).toList();
        //System.out.println(l);
        return l.stream().min(Long::compare).orElse(0L);
    }

    public long findRangeSeed2() {
        // THIS IS IMPOSSIBLE TO COMPUTE IN A REASONABLE TIME LIMIT
        List<Long> actualSeeds = IntStream.range(0, seeds.size()).filter(i -> i % 2 == 0).mapToObj(seeds::get).toList();
        List<Long> counts = IntStream.range(0, seeds.size()).filter(i -> i % 2 != 0).mapToObj(seeds::get).toList();
        return IntStream.range(0, seeds.size() / 2)
                .mapToObj(i ->
                        LongStream.range(actualSeeds.get(i), actualSeeds.get(i) + counts.get(i))
                                .boxed().map(this::search).min(Long::compare).orElse(0L))
                .min(Long::compare).orElse(0L);

    }

    private long search(Long seed) {
        //System.out.println("\nThe original seed: " + seed);
        AtomicLong l = new AtomicLong(seed);
        words.forEach(word -> {
            //System.out.println("Number: " + l.get());
            // Find the smallest but closest integer for the first map
            long c = word.stream().map(i -> Long.parseLong(i.get(1))).filter(i -> i <= l.get()).max(Long::compare).orElse(0L);
            //System.out.println("c: " + c);
            // Find corresponding offset
            long offset = word.stream().filter(i -> (Long.parseLong(i.get(1)) == c)).map(i -> Long.parseLong(i.get(0)) - Long.parseLong(i.get(1))).toList().get(0);
            //System.out.println("offset: " + offset);
            // Find corresponding number
            long num = Long.parseLong(word.stream().filter(i -> (Long.parseLong(i.get(1)) == c)).map(i -> i.get(2)).toList().get(0));
            //System.out.println("num: " + num);
            //System.out.println((c + num - 1) + " bigger than " + l.get() + " ? " + (c + num - 1 >= l.get()));
            // Replace value if the seed is included, assigns itself otherwise
            l.set(c + num - 1 >= l.get() ? l.get() + offset : l.get());
        });

        return l.get();

    }

    public Long findRangeSeed(){
        AtomicReference<List<Pair<Long, Long>>> ret = new AtomicReference<>(seedRanges);

        IntStream.range(0, words.size()).forEach(i -> {
            // System.out.println("\nIteration " + (i + 1) + ":\n");
            List<Pair<Long, Long>> temp;
            temp = mergeRanges(findRange(i), ret.get());
            temp.sort(Comparator.comparingLong(Pair::key));
            ret.set(temp);
            // System.out.println("\nret for iteration " + (i + 1) + ": ");
            // ret.get().forEach(System.out::println);
        });
        return ret.get().get(0).key();
    }

    // Returns a list of ranges in a map sorted by keys
    public List<Pair<Pair<Long, Long>, Pair<Long, Long>>> findRange(int i) {
        List<List<String>> wordList = words.get(i);

        wordList.sort(Comparator.comparingLong(o -> Long.parseLong(o.get(0))));

        // Create a pair of pairs of range, range: "a b c" becomes ([a, a+c-1], [b, b+c-1])
        List<Pair<Pair<Long, Long>, Pair<Long, Long>>> ret = new ArrayList<>(wordList.stream().map(l -> new Pair<>(
                new Pair<>(Long.parseLong(l.get(0)), Long.parseLong(l.get(0))+Long.parseLong(l.get(2))-1),
                new Pair<>(Long.parseLong(l.get(1)), Long.parseLong(l.get(1))+Long.parseLong(l.get(2))-1)
        )).toList());

        List<Pair<Pair<Long, Long>, Pair<Long, Long>>> temp = new ArrayList<>();

        // Adding back the identity functions
        IntStream.range(1, ret.size()).forEach(j -> {
            if (ret.get(j).key().key() - ret.get(j - 1).key().value() != 1) {
                temp.add(new Pair<>(
                        new Pair<>(ret.get(j - 1).key().value() + 1, ret.get(j).key().key() - 1),
                        new Pair<>(ret.get(j - 1).key().value() + 1, ret.get(j).key().key() - 1)
                ));
            }
        });

        ret.addAll(temp);
        ret.sort(Comparator.comparingLong(k -> k.key().key()));
        return ret;
    }

    private Long offset(Pair<Pair<Long, Long>, Pair<Long, Long>> pair) {
        return pair.value().key() - pair.key().key();
    }

    private List<Pair<Long, Long>> mergeRanges(List<Pair<Pair<Long, Long>, Pair<Long, Long>>> l, List<Pair<Long, Long>> input) {
        List<Pair<Long, Long>> ret = new ArrayList<>();
        input.forEach(s ->
                {
                    // This is the list of intervals that intersect with offset
                    List<Pair<Long, Long>> n = new ArrayList<>(l.stream().map(k -> intersectionOffset(s, k.key(), offset(k)))
                            .filter(Objects::nonNull).toList());

                    n.sort(Comparator.comparingLong(Pair::key));

                    // System.out.println("[" + s.key() + ", " + s.value() + "] became ");
                    // n.forEach(System.out::println);

                    // This contains the list of intervals that intersect without offset
                    List<Pair<Long, Long>> temp = l.stream().map(k -> intersection(s, k.key())).filter(Objects::nonNull).toList();

                    // Remaining unchanged intervals
                    List<Pair<Long, Long>> n2 = temp.isEmpty() ? new ArrayList<>() : pairs(s, temp);

                    // if (!n2.isEmpty()) {
                        // System.out.println("with");
                        // n2.forEach(System.out::println);
                    // }
                    // System.out.println("\n");

                    n2.addAll(n);
                    n2.sort(Comparator.comparingLong(Pair::key));
                    ret.addAll(n2);
                }
        );
        return ret;
    }

    private List<Pair<Long, Long>> pairs(Pair<Long, Long> s, List<Pair<Long, Long>> n) {
        List<Pair<Long, Long>> n2 = new ArrayList<>();

        long start = s.key().equals(n.get(0).key()) ? n.get(0).value() + 1 : s.key();
        for (int count = start == s.key() ? 0 : 1; start <= n.get(n.size() - 1).value(); ++count) {
            if (n.get(count).key() - 1 >= start)
                n2.add(new Pair<>(start, n.get(count).key() - 1));
            start = n.get(count).value() + 1;
        }

        if (start <= s.value())
            n2.add(new Pair<>(start, s.value()));

        return n2;
    }

    private Pair<Long, Long> intersectionOffset(Pair<Long, Long> p1, Pair<Long, Long> p2, long offset) {
        if (p1.key() > p2.value() || p2.key() > p1.value())
            return null;
        // System.out.println(p1 + " and " + p2 + "and offset is: " + offset);
        return new Pair<>(Math.max(p1.key(), p2.key()) + offset, Math.min(p1.value(), p2.value()) + offset);
    }

    private Pair<Long, Long> intersection(Pair<Long, Long> p1, Pair<Long, Long> p2) {
        if (p1.key() > p2.value() || p2.key() > p1.value())
            return null;
        return new Pair<>(Math.max(p1.key(), p2.key()), Math.min(p1.value(), p2.value()));
    }
}

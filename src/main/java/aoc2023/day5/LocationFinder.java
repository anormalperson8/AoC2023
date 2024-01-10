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
        // System.out.println("\nThe original seed: " + seed);
        AtomicLong l = new AtomicLong(seed);
        words.forEach(word -> {
            // System.out.println("Number: " + l.get());
            // Find the smallest but closest integer for the first map
            long c = word.stream().map(i -> Long.parseLong(i.get(1))).filter(i -> i <= l.get()).max(Long::compare).orElse(0L);
            // System.out.println("c: " + c);
            // Find corresponding offset
            long offset = word.stream().filter(i -> (Long.parseLong(i.get(1)) == c)).map(i -> Long.parseLong(i.get(0)) - Long.parseLong(i.get(1))).toList().get(0);
            // System.out.println("offset: " + offset);
            // Find corresponding number
            long num = Long.parseLong(word.stream().filter(i -> (Long.parseLong(i.get(1)) == c)).map(i -> i.get(2)).toList().get(0));
            // System.out.println("num: " + num);
            // System.out.println((c + num - 1) + " bigger than " + l.get() + " ? " + (c + num - 1 >= l.get()));
            // Replace value if the seed is included, assigns itself otherwise
            l.set(c + num - 1 >= l.get() ? l.get() + offset : l.get());
        });

        return l.get();

    }

    public Long findRangeSeed(){
        AtomicReference<List<Pair<Long, Long>>> ret = new AtomicReference<>(seedRanges);

        IntStream.range(0, words.size()).forEach(i -> {
            // System.out.println("\nIteration " + (i + 1) + ":");
            List<Pair<Long, Long>> temp;
            temp = mergeRanges(findRange(i), ret.get());
            temp.sort(Comparator.comparingLong(Pair::key));
            ret.set(temp);
            // System.out.println("\nret for iteration " + (i + 1) + ": ");
            // ret.get().forEach(System.out::println);
            // System.out.println("Sum: " + ret.get().stream().map(k -> k.value() - k.key() + 1).reduce(Long::sum).orElseThrow());
        });
        return ret.get().get(0).key();
    }

    private List<Pair<Pair<Long, Long>, Pair<Long, Long>>> findRange(int index) {
        List<Pair<Pair<Long, Long>, Pair<Long, Long>>> mappings = new ArrayList<>();
        List<List<Long>> l = words.get(index).stream().map(i -> i.stream().map(Long::parseLong).toList())
                .sorted(Comparator.comparingLong(i -> i.get(1))).toList();
        l.forEach(item ->
                mappings.add(new Pair<>(
                        new Pair<>(item.get(1), item.get(1) + item.get(2) - 1),
                        new Pair<>(item.get(0), item.get(0) + item.get(2) - 1))
                )
        );

        long count = 0L;
        int i = 0;
        while (count < mappings.get(mappings.size() - 1).key().value()) {
            if (count != mappings.get(i).key().key()) {
                mappings.add(new Pair<>(
                        new Pair<>(count, mappings.get(i).key().key() - 1),
                        new Pair<>(count, mappings.get(i).key().key() - 1))
                );
            }
            count = mappings.get(i++).key().value() + 1;
        }

        mappings.sort(Comparator.comparingLong(k -> k.key().key()));

        if (mappings.get(mappings.size() - 1).key().value() < 4294967295L) {
            mappings.add(new Pair<>(
                    new Pair<>(mappings.get(mappings.size() - 1).key().value() + 1, 4294967295L),
                    new Pair<>(mappings.get(mappings.size() - 1).key().value() + 1, 4294967295L))
            );
        }

        // mappings.forEach(System.out::println);

        // mappings.sort(Comparator.comparingLong(k -> k.key().key()));

        // System.out.println("Max:" + mappings.get(mappings.size() - 1).key().value());

        return mappings;
    }

    private List<Pair<Long, Long>> mergeRanges(List<Pair<Pair<Long, Long>, Pair<Long, Long>>> mappings, List<Pair<Long, Long>> ranges) {
        // Get the intersections
        List<Pair<Long, Long>> temp = new ArrayList<>();

        mappings.forEach(mapping ->
                ranges.forEach(range ->
                        temp.add(intersectionOffset(mapping.key(), range, offset(mapping)))
                )
        );

        return new ArrayList<>(temp.stream().filter(Objects::nonNull).toList());
    }

    private long offset(Pair<Pair<Long, Long>, Pair<Long, Long>> pair) {
        return pair.value().key() - pair.key().key();
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

package aoc2023.day5;

import aoc2023.FileInput;
import aoc2023.Pair;

import java.io.File;
import java.util.Comparator;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("--Advent Of Code Day 5--");
        FileInput f = new FileInput("input" + File.separator + "AoC2023_5.txt");
        LocationFinder lf = new LocationFinder(f.getLines());
        System.out.println("Part 1: " + lf.findSeed());

        LocationFinder lf2 = new LocationFinder(f.getLines());
        System.out.println("Part 2: " + lf2.findRangeSeed());
    }
}

package aoc2023.day4;

import aoc2023.FileInput;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        System.out.println("--Advent Of Code Day 4--");
        FileInput f = new FileInput("input" + File.separator + "AoC2023_4.txt");

        CardMatcher cm = new CardMatcher(f.getLines());
        System.out.println("Part 1: " + cm.sum());
        CardCounter cc = new CardCounter(f.getLines());
        System.out.println("Part 2: " + cc.count());
    }
}

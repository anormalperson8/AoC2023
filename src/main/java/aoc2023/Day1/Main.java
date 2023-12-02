package aoc2023.Day1;

import aoc2023.FileInput;

import java.io.File;

public class Main {


    public static void main(String[] args) {
        System.out.println("--Advent Of Code Day 1--");
        FileInput f = new FileInput("input" + File.separator + "AoC2023_1.txt");
        NumberParser np = new NumberParser(f.getLines());
        System.out.println("Part 1: " + np.getSum());
        NumberStringParser nsp = new NumberStringParser(f.getLines());
        System.out.println("Part 2: " + nsp.getSum());

        System.out.println("Using alternate class:");
        AlternateNumberStringParser ansp = new AlternateNumberStringParser(f.getLines(), false);
        System.out.println("Part 1: " + ansp.getSum());
        AlternateNumberStringParser ansp2 = new AlternateNumberStringParser(f.getLines(), true);
        System.out.println("Part 2: " + ansp2.getSum());
    }
}

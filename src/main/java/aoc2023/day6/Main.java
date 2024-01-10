package aoc2023.day6;

import aoc2023.FileInput;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        System.out.println("--Advent Of Code Day 6--");
        FileInput f = new FileInput("input" + File.separator + "AoC2023_6.txt");

        RaceBeater rb = new RaceBeater(f.getLines());
        System.out.println("Part 1: " + rb.win());

        System.out.println("Part 2: " + rb.bigWin());
    }
}

package aoc2023.day7;

import aoc2023.FileInput;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        System.out.println("--Advent Of Code Day 7--");
        FileInput f = new FileInput("input" + File.separator +/*"temp.txt"*/"AoC2023_7.txt");

        CardComparer cc = new CardComparer(f.getLines());
        System.out.println("Part 1: " + cc.winnings());

        System.out.println("Part 2: " + cc.winningsWithJoker());
    }
}

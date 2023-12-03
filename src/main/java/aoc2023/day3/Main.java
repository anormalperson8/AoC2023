package aoc2023.day3;

import aoc2023.FileInput;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        System.out.println("--Advent Of Code Day 3--");
        FileInput f = new FileInput("input" + File.separator + "AoC2023_3.txt");
        PartRecogniser pr = new PartRecogniser(f.getLines());
        System.out.println("Part 1: " + pr.check());
        PartRecogniser gr = new GearRecogniser(f.getLines());
        System.out.println("Part 2: " + gr.check());
    }
}

package AoC2023.Day2;

import AoC2023.FileInput;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        System.out.println("--Advent Of Code Day 2--");
        FileInput f = new FileInput("input" + File.separator + "AoC2023_2.txt");
        CubeGameStringParser cbsp = new CubeGameStringParser(f.getLines());
        System.out.println("Part 1: " + cbsp.get());
        CubeGameStringNumberGuesser cbsng = new CubeGameStringNumberGuesser(f.getLines());
        System.out.println("Part 2: " + cbsng.get());
    }
}

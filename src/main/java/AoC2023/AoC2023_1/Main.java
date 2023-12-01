package AoC2023.AoC2023_1;

import AoC2023.FileInput;

import java.io.File;

public class Main {


    public static void main(String[] args) {
        FileInput f = new FileInput("input" + File.separator + "AoC2023_1.txt");
        NumberTurner n = new NumberTurner(f.getLines());
        System.out.println("Sum 1: " + n.getSum());
        System.out.println("Sum 2: " + n.getSum2());
    }
}
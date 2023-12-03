package aoc2023;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class FileInputTest {
    @Test
    void testTest() {
        FileInput fileInputTest = new FileInput( "input" + File.separator + "FileInputTest.txt");
        List<String> l = new ArrayList<>();
        IntStream.range(0, 16).forEach(i -> l.add("abc"));
        assertArrayEquals(l.toArray(), fileInputTest.getLines().toArray());
        assertArrayEquals(l.subList(0, 10).toArray(), fileInputTest.test().toArray());
    }

    @Test
    void testException() {
        assertThrows(RuntimeException.class, () -> new FileInput("input" + File.separator + "AoC2023_26.txt"));
    }
}
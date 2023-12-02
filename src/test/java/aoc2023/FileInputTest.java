package aoc2023;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class FileInputTest {

    private FileInput fileInput;
    private int a = 1;

    @BeforeEach
    public void setUp() {
        fileInput = new FileInput("input" + File.separator + "AoC2023_" + a++ + ".txt");
    }

    @Test
    void testTest() {
        String testString = """
                threehqv2
                sxoneightoneckk9ldctxxnffqnzmjqvj
                1hggcqcstgpmg26lzxtltcgg
                nrhoneightkmrjkd7fivesixklvvfnmhsldrc
                zhlzhrkljonephkgdzsnlglmxvprlh6n
                594chhplnzsxktjmkfpninefiveczfnvsctbxcfzfzjh
                seven2tjf
                five712
                tzheightzlzmsqlnxfqzrlbhbdpbnbdjns6
                ztwo1eight95""";
        assertEquals(testString, String.join("\n", fileInput.test()));
    }
}
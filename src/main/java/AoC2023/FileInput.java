package AoC2023;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FileInput {
    private final List<String> lines = new ArrayList<>();

    public FileInput(String fileName){
        this.read(fileName);
    }

    private void read(String fileName) {
        try (Stream<String> stream = Files.lines(Path.of(fileName))) {
            stream.forEach(this.lines::add);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't read the file.");
        }
    }

    public void test() {
        IntStream.range(0, 10).forEach(i -> System.out.println(this.lines.get(i)));
    }

    public List<String> getLines() {
        return this.lines;
    }
}


package AoC2023;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FileInput {
    private final List<String> lines = new ArrayList<>();
    private final String fileName;

    public FileInput(String fileName){
        this.fileName = fileName;
        this.read();
    }

    private void read() {
        try (Stream<String> stream = Files.lines(Paths.get(this.fileName))) {
            stream.forEach(lines::add);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't read the file.");
        }
    }

    public void test() {
        IntStream.range(0, 10).forEach(i -> System.out.println(lines.get(i)));
    }

    public List<String> getLines() {
        return this.lines;
    }
}

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
    private final String fileName;

    public FileInput(String fileName){
        this.fileName = fileName;
        this.read();
    }

    private void read() {
        try (Stream<String> stream = Files.lines(Path.of(this.fileName))) {
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

class A {
  void b() {
    try {
        Stream<Integer> s = Stream.of(1, 2, 3);
        s.filter(i -> i != -1);
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
  }
}

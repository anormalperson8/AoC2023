package aoc2023.day3;

import aoc2023.BiMap;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.IntStream;

public class PartRecogniser {
    protected final List<String> lines;
    protected final List<BiMap<String, Integer>> wordToIndex = new ArrayList<>();
    protected int lineNum = 0;
    private int i = 0;


    public PartRecogniser(List<String> lines) {
        this.lines = new ArrayList<>(lines);
        IntStream.range(0, lines.size()).forEach(i -> this.wordToIndex.add(new BiMap<>()));

        this.lines.forEach(j -> {
            classify(j);
            ++lineNum;
        });

        lineNum = 0;
        //wordToIndex.forEach(System.out::println);
    }

    private void classify(String line) {

        // Get rid of all leading '.' characters
        while (line.startsWith(".")){
            line = line.substring(1);
            ++i;
        }

        // Base case: empty string
        if (line.isEmpty()) {
            i = 0;
            return;
        }

        // Check if there are symbol next to numbers
        int end = line.contains(".") ? line.indexOf(".") : line.length();
        String s = line.substring(0, end);

        String newS = s;

        if (s.matches(".*\\d.*")){
            newS = s.replaceAll("[^0-9]", "").replaceAll("\\*", "").replaceAll("/", "");
        }

        //System.out.print("Putting in ");

        // No sticking between symbols and numbers
        if (newS.length() == line.substring(0, end).length()){
            //System.out.printf("%3s", line.substring(0, end));
            wordToIndex.get(lineNum).put(line.substring(0, end), i);
            //System.out.println(" at line " + lineNum + "(1)");
            i += end;
            classify(line.substring(end));
        }
        // The first character is non-numeric
        else if (!s.substring(0, 1).matches(".*\\d.*")) {
            //System.out.printf("%3s", s.charAt(0));
            wordToIndex.get(lineNum).put(s.substring(0, 1), i++);
            //System.out.println(" at line " + lineNum + "(2)");
            classify(line.substring(1));
        }
        // The characters after number is non-numeric
        else {
            int count = 0;
            while (s.charAt(count) >= '0' && s.charAt(count) <= '9') ++count;
            //System.out.printf("%3s", s.substring(0, count));
            wordToIndex.get(lineNum).put(s.substring(0, count), i);
            //System.out.println(" at line " + lineNum + "(3)");
            i += count;
            classify(line.substring(count));
        }

    }

    public int check() {
        return wordToIndex.stream().map(this::checkLine).mapToInt(Integer::intValue).sum();
    }

    private int checkLine(BiMap<String, Integer> m) {
        int sum = 0;

        sum += m.keyList().stream().filter(s -> s.matches(".*\\d.*")).filter(s -> this.compare(m, s))
                .map(Integer::parseInt).mapToInt(Integer::intValue).sum();

        ++lineNum;
        return sum;
    }

    private boolean compare(BiMap<String, Integer> m, String s) {
        int startIndex = m.getValue(s) - 1;
        int endIndex = m.getValue(s) + s.length();

        //System.out.println("For string " + s + " in line " + lineNum + " (start: " + startIndex + ", end: " + endIndex + "):");

        // Check before and after
        if (m.containsValue(startIndex) || m.containsValue(endIndex))
            return true;

        // Check next row
        if (lineNum != lines.size() - 1) {
            BiMap<String, Integer> next = wordToIndex.get(lineNum + 1);
            //System.out.println(Arrays.toString(IntStream.range(startIndex, endIndex + 1).filter(next::containsValue).toArray()));
            if (!IntStream.range(startIndex, endIndex + 1).filter(next::containsValue)
                    .mapToObj(next::getKey).filter(w -> !w.matches(".*\\d.*")).toList().isEmpty())
                return true;
        }

        // Check previous row
        if (lineNum != 0) {
            BiMap<String, Integer> prev = wordToIndex.get(lineNum - 1);
            return !IntStream.range(startIndex, endIndex + 1).filter(prev::containsValue)
                    .mapToObj(prev::getKey).filter(w -> !w.matches(".*\\d.*")).toList().isEmpty();
        }

        return false;
    }

    protected boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }
}

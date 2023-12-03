package aoc2023.day3;

import aoc2023.BiMap;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class GearRecogniser extends PartRecogniser {

    public GearRecogniser(List<String> lines) {
        super(lines);
    }

    @Override
    public int check() {
        return wordToIndex.stream().map(this::checkLine).mapToInt(Integer::intValue).sum();
    }

    private int checkLine(BiMap<String, Integer> m) {
        int sum = 0;
        for (int count = 0; count < m.keyList().size(); ++count) {
            if (m.keyList().get(count).equals("*")) {
                List<Integer> gears = new ArrayList<>();
                final int startIndex = m.valueList().get(count) - 1;
                final int endIndex = m.valueList().get(count) + 1;
                // Check same row
                char before = lines.get(lineNum).charAt(startIndex);
                if (isDigit(before)) {
                    int index = startIndex;
                    // Find the starting index of the number before
                    while (index != -1 && isDigit(lines.get(lineNum).charAt(index))){--index;}
                    gears.add(Integer.parseInt(m.getKey(index + 1)));
                }
                char after = lines.get(lineNum).charAt(endIndex);
                if (isDigit(after)) {
                    gears.add(Integer.parseInt(m.getKey(endIndex)));
                }

                // Check next row
                if (lineNum != lines.size() - 1) {
                    char beforeNext = lines.get(lineNum + 1).charAt(startIndex);
                    if (isDigit(beforeNext)) {
                        int index = startIndex;
                        // Find the starting index of the number before
                        while (index != -1 && isDigit(lines.get(lineNum + 1).charAt(index))){--index;}
                        gears.add(Integer.parseInt(wordToIndex.get(lineNum + 1).getKey(index + 1)));
                    }
                    IntStream.range(startIndex + 1, endIndex + 1).filter(wordToIndex.get(lineNum + 1)::containsValue).boxed()
                            .forEach(n -> gears.add(Integer.parseInt(wordToIndex.get(lineNum + 1).getKey(n))));
                }

                // Check previous row
                if (lineNum != 0) {
                    char afterNext = lines.get(lineNum - 1).charAt(startIndex);
                    if (isDigit(afterNext)) {
                        int index = startIndex;
                        // Find the starting index of the number before
                        while (index != -1 && isDigit(lines.get(lineNum - 1).charAt(index))){--index;}
                        gears.add(Integer.parseInt(wordToIndex.get(lineNum - 1).getKey(index + 1)));
                    }
                    IntStream.range(startIndex + 1, endIndex + 1).filter(wordToIndex.get(lineNum - 1)::containsValue).boxed()
                            .forEach(n -> gears.add(Integer.parseInt(wordToIndex.get(lineNum - 1).getKey(n))));
                }
                sum += gears.size() >= 2 ? gears.stream().reduce(1, (g1, g2) -> g1 * g2) : 0;
            }
        }

        ++lineNum;
        return sum;
    }
}

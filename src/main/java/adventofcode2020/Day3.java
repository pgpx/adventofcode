package adventofcode2020;

import com.google.common.collect.Streams;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Day3 extends Solver {
    public static void main(String[] args) throws URISyntaxException, IOException {
        new Day3().start("example-3.txt", "input-3.txt");
    }

    @Override
    protected long part1(Stream<String> s) {
        return solve(s.collect(toList()), 3, 1);
    }

    @Override
    protected long part2(Stream<String> s) {
        var lines = s.collect(toList());
        return solve(lines, 1, 1) *
                solve(lines, 3, 1) *
                solve(lines, 5, 1) *
                solve(lines, 7, 1) *
                solve(lines, 1, 2);
    }

    private long solve(List<String> lines, int incCol, int incRow) {
        int col = 0;
        long count = 0;
        for (int row = 0; row < lines.size(); row += incRow) {
            var line = lines.get(row);
            if (line.charAt(col) == '#') {
                count++;
            }
            col = (col + incCol) % line.length();
        }
        return count;
    }

    public static long streamSolve(Stream<String> s, int incCol, int incRow) {
        return Streams.mapWithIndex(s, SimpleImmutableEntry::new)
                .filter(e -> {
                    var row = e.getValue();
                    if (row % incRow != 0) {
                        return false;
                    }
                    var k = e.getKey();
                    var col = (int) (row / incRow * incCol) % k.length();
                    return k.charAt(col) == '#';
                })
                .count();
    }
}
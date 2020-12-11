package adventofcode2020;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.stream.Stream;

public class Day1 extends Solver {
    public static void main(String[] args) throws URISyntaxException, IOException {
        new Day1().start("example-1.txt", "input-1.txt");
    }

    private static final int target = 2020;

    private static long[] longArray(Stream<String> s) {
        return s.mapToLong(Long::parseLong).sorted().toArray();
    }

    @Override
    protected long part1(Stream<String> s) {
        var values = longArray(s);
        var insertion = 1 - Arrays.binarySearch(values, target / 2);

        int b = values.length - 1;
        for (int a = 0; a < insertion; a++) {
            while (values[a] + values[b] > target && b > insertion) {
                b--;
            }
            if (values[a] + values[b] == target) {
                return values[a] * values[b];
            }
        }
        throw new RuntimeException("No solution found");
    }

    @Override
    protected long part2(Stream<String> s) {
        var values = longArray(s);
        var limit = binarySearch(values, 0, values.length, target / 3 + 1);

        for (var c = values.length - 1; c >= limit - 1; c--) {
            for (var  b = binarySearch(values, 0, c, target - values[c]); b > 0; b--) {
                var a = binarySearch(values, 0, b, target - values[c] - values[b]);
                if (values[c] + values[b] + values[a] == target) {
                    return values[a] * values[b] * values[c];
                }
            }
        }
        throw new RuntimeException("No solution found");
    }

    private static int binarySearch(long[] a, int fromIndex, int toIndex, long key) {
        var res = Arrays.binarySearch(a, fromIndex, toIndex, key);
        return (res >= 0) ? res : 1 - res;
    }

}
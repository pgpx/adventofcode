package adventofcode2020;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

public class Day1 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        System.out.println("Solving test");
        solve(2020, new int[]{1721, 979, 366, 299, 675, 1456});

        System.out.println("Solving actual");
        try (var s = Util.readFile(Day1.class, "input-1.txt")) {
            solve(2020, s.mapToInt(Integer::valueOf).toArray());
        }
    }

    public static void solve(int target, int[] values) {
        solvePart1(target, values);
        solvePart2(target, values);
    }

    public static void solvePart1(int target, int[] values) {
        Arrays.sort(values);
        var insertion = 1 - Arrays.binarySearch(values, target / 2);

        int b = values.length - 1;
        for (int a = 0; a < insertion; a++) {
            while (values[a] + values[b] > target && b > insertion) {
                b--;
            }
            if (values[a] + values[b] == target) {
                System.out.println("Part 1: Found " + values[a] +
                                           " + " + values[b] +
                                           " = " + target +
                                           " -> " + values[a] * values[b]);
                return;
            }
        }
        System.out.println("Part 1: No solution found");
    }

    private static int binarySearch(int[] a, int fromIndex, int toIndex, int key) {
        var res = Arrays.binarySearch(a, fromIndex, toIndex, key);
        return (res >= 0) ? res : 1 - res;
    }

    public static void solvePart2(int target, int[] values) {
        Arrays.sort(values);

        var limit = binarySearch(values, 0, values.length, target / 3 + 1);

        for (int c = values.length - 1; c >= limit - 1; c--) {
            for (int b = binarySearch(values, 0, c, target - values[c]); b > 0; b--) {
                var a = binarySearch(values, 0, b, target - values[c] - values[b]);
                if (values[c] + values[b] + values[a] == target) {
                    System.out.println("Part 2: Found " + values[a] +
                                               " + " + values[b] +
                                               " + " + values[c] +
                                               " = " + target +
                                               " -> " + values[a] * values[b] * values[c]);
                    return;
                }
            }
        }
        System.out.println("Part 2: No solution found");
    }
}
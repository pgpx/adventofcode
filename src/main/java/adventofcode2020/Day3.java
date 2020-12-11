package adventofcode2020;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.stream.Collectors;

public class Day3 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        System.out.println("Solving example");
        solve("example-3.txt");

        System.out.println("Solving actual");
        solve("input-3.txt");
    }

    public static void solve(String filename) throws IOException, URISyntaxException {
        System.out.println("Part 1: " + solve(filename, 3, 1));

        var res2 = solve(filename, 1, 1) *
                solve(filename, 3, 1) *
                solve(filename, 5, 1) *
                solve(filename, 7, 1) *
                solve(filename, 1, 2);

        System.out.println("Part 2: " + res2);
    }

    public static long solve(String filename, int incCol, int incRow) throws IOException, URISyntaxException {
        int col = 0;
        long count = 0;
        var lines =  Util.readFile(Day3.class, filename).collect(Collectors.toList());
        for (int row = 0; row < lines.size(); row += incRow) {
//            System.out.println(line.charAt(col));
            var line = lines.get(row);
            if (line.charAt(col) == '#') {
                count++;
            }
            col = (col + incCol) % line.length();
        }
        return count;

    }
}
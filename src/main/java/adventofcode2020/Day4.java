package adventofcode2020;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.BitSet;

public class Day4 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        System.out.println("Solving example");
        solve("example-4.txt");

        System.out.println("Solving actual");
        solve("input-4.txt");
    }

    public static void solve(String filename) throws IOException, URISyntaxException {
        System.out.println("Part 1: " + maxSeatId(filename));
        System.out.println("Part 2: " + finder(filename));
    }

    private static int seatId(String line) {
        return Integer.parseInt(
                line.replace('B', '1')
                        .replace('F', '0')
                        .replace('R', '1')
                        .replace('L', '0'),
                2);
    }

    public static int finder(String filename) throws IOException, URISyntaxException {
        int range = 2 << 10;
        try (var s = Util.readFile(Day4.class, filename)) {
            var taken = s.mapToInt(Day4::seatId)
                    .collect(BitSet::new, BitSet::set, BitSet::or);

            taken.set(0, taken.nextSetBit(0));
            taken.set(taken.previousSetBit(range), range);
            return taken.nextClearBit(0);
        }
    }

    public static int maxSeatId(String filename) throws IOException, URISyntaxException {
        try (var s = Util.readFile(Day4.class, filename)) {
            return s.mapToInt(Day4::seatId).max().orElse(0);
        }
    }
}
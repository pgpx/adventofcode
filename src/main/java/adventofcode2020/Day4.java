package adventofcode2020;

import com.google.common.primitives.Ints;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.BitSet;
import java.util.Comparator;
import java.util.stream.Collectors;

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
        var taken = Util.readFile(Day4.class, filename)
                .map(Day4::seatId)
                .reduce(new BitSet(range),
                        (bs, id) -> {
                            bs.set(id); return bs;
                        },
                        (a, b) -> {
                            a.or(b); return a;
                        });
        taken.set(0, taken.nextSetBit(0));
        taken.set(taken.previousSetBit(range), range);
        return taken.nextClearBit(0);
    }

    public static int finder2(String filename) throws IOException, URISyntaxException {
        return finder(Ints.toArray(
                Util.readFile(Day4.class, filename)
                        .map(Day4::seatId)
                        .collect(Collectors.toList())));
    }

    public static int finder(int[] seatIds) {
        int range = 2 << 10;
        BitSet taken = new BitSet(range);
        for (int id : seatIds) {
            taken.set(id);
        }
        taken.set(0, taken.nextSetBit(0));
        taken.set(taken.previousSetBit(range), range);
        return taken.nextClearBit(0);
    }

    public static int maxSeatId(String filename) throws IOException, URISyntaxException {
        return Util.readFile(Day4.class, filename)
                .map(Day4::seatId)
                .max(Comparator.naturalOrder()).orElse(0);
    }
}
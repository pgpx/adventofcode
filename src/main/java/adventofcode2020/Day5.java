package adventofcode2020;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.BitSet;
import java.util.stream.Stream;

public class Day5 extends Solver {
    public static void main(String[] args) throws URISyntaxException, IOException {
        new Day5().start("example-5.txt", "input-5.txt");
    }

    @Override
    protected long part1(Stream<String> s) {
        return s.mapToInt(Day5::seatId).max().orElseThrow();
    }

    @Override
    protected long part2(Stream<String> s) {
        int range = 2 << 10;

        var taken = s.mapToInt(Day5::seatId)
                .collect(BitSet::new, BitSet::set, BitSet::or);

        taken.set(0, taken.nextSetBit(0));
        taken.set(taken.previousSetBit(range), range);
        return taken.nextClearBit(0);
    }

    private static int seatId(String line) {
        return Integer.parseInt(
                line.replace('B', '1')
                        .replace('F', '0')
                        .replace('R', '1')
                        .replace('L', '0'),
                2);
    }
}
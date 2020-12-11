package adventofcode2020;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.BitSet;
import java.util.HashSet;
import java.util.stream.Stream;

import static java.util.function.Function.identity;

public class Day6 extends Solver {
    public static void main(String[] args) throws URISyntaxException, IOException {
        new Day6().solve("example-6.txt", 11, 6);
        new Day6().solve("input-6.txt", 6742, 3447);
    }

    @Override
    protected long part1(Stream<String> stream) {
        return paragraphs(stream)
                .mapToInt(p -> p.map(String::chars)
                        .flatMapToInt(identity())
                        .collect(HashSet::new, HashSet::add, HashSet::addAll)
                        .size())
                .sum();
    }

    @Override
    protected long part2(Stream<String> stream) {
        return paragraphs(stream)
                .mapToInt(p -> p.map(l -> l.chars().map(ch -> ch - 'a').collect(BitSet::new, BitSet::set, BitSet::or))
                        .collect(() -> BitSet.valueOf(new long[]{(1 << 26) - 1}), BitSet::and, BitSet::and)
                        .cardinality())
                .sum();
    }
}
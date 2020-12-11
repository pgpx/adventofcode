package adventofcode2020;

import com.google.common.base.Strings;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.ToLongFunction;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public abstract class Solver {

    public void solve(String filename, long expected1, long expected2) throws IOException, URISyntaxException {
        System.out.println("Solving " + this.getClass().getSimpleName() + " using " + filename);
        process("Part 1: ", filename, expected1, this::part1);
        process("Part 2: ", filename, expected2, this::part2);
    }
    protected void process(String label, String filename, long expected, ToLongFunction<Stream<String>> part) throws IOException, URISyntaxException {
        try (var s = readFile(filename)) {
            long actual = part.applyAsLong(s);
            System.out.println(label + actual);
            if (actual != expected) {
                throw new RuntimeException("Expected " + expected + " but got " + actual);
            }
        }
    }

    private Stream<String> readFile(String filename) throws URISyntaxException, IOException {
        return Files.lines(Path.of(this.getClass().getResource(filename).toURI()))
                .filter(Objects::nonNull);
    }

    protected abstract long part1(Stream<String> stream);

    protected abstract long part2(Stream<String> stream);

    /**
     * Groups lines split by blank lines
     */
    protected Stream<Stream<String>> paragraphs(Stream<String> s) {
        ParagraphAccumulator acc = new ParagraphAccumulator();
        for (var l : s.collect(toList())) {
            acc.add(l);
        }
        return acc.stream();
    }

    private static class ParagraphAccumulator {
        private List<Stream<String>> l = new ArrayList<>();
        List<String> current = null;

        void add(String val) {
            if (Strings.isNullOrEmpty(val)) {
                finishCurrent();
            } else {
                current = Objects.requireNonNullElse(current, new ArrayList<>());
                current.add(val);
            }
        }

        void finishCurrent() {
            if (current != null) {
                l.add(current.stream());
                current = null;
            }
        }

        Stream<Stream<String>> stream() {
            finishCurrent();
            return l.stream();
        }
    }
}
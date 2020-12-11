package adventofcode2020;

import com.google.common.base.Strings;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public abstract class Solver {
    public void start(String exampleFilename, String inputFilename) throws URISyntaxException, IOException {
        System.out.println("Solving example");
        solve(exampleFilename);

        System.out.println("Solving actual");
        solve(inputFilename);
    }

    protected void solve(String filename) throws IOException, URISyntaxException {
        try (var s = readFile(filename)) {
            System.out.println("Part 1: " + part1(s));
        }

        try (var s = readFile(filename)) {
            System.out.println("Part 2: " + part2(s));
        }
    }

    private Stream<String> readFile(String filename) throws URISyntaxException, IOException {
        return Files.lines(Path.of(this.getClass().getResource(filename).toURI()))
                .filter(Objects::nonNull);
    }

    protected abstract long part1(Stream<String> s);

    protected abstract long part2(Stream<String> s);

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
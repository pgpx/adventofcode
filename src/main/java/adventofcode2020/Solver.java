package adventofcode2020;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.stream.Stream;

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
}
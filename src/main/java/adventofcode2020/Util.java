package adventofcode2020;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Util {
    public static Stream<String> readFile(Class<?> clazz, String path) throws URISyntaxException, IOException {
        return Files.lines(Path.of(clazz.getResource(path).toURI()))
                .filter(Objects::nonNull);
    }

    public static List<String> readFileAsList(Class<?> clazz, String path) throws URISyntaxException, IOException {
        try (var s = readFile(clazz, path)) {
            return s.collect(toList());
        }
    }
}

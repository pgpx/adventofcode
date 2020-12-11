package adventofcode2020;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.stream.Stream;

public class Util {
    public static Stream<String> readFile(Class<?> clazz, String path) throws URISyntaxException, IOException {
        return Files.lines(Path.of(clazz.getResource(path).toURI()))
                .filter(Objects::nonNull);
    }
}

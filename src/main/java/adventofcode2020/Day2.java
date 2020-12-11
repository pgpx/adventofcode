package adventofcode2020;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day2 extends Solver {

    public static void main(String[] args) throws URISyntaxException, IOException {
        new Day2().start("example-2.txt", "input-2.txt");
    }

    private static final Pattern LINE_REGEX = Pattern.compile("(\\d+)-(\\d+)\\s+(.):\\s+(.*)");

    @FunctionalInterface
    private interface Validator {
        boolean validate(int min, int max, char ch, String password);
    }

    private static boolean isValid(String line, Validator v) {
        var match = LINE_REGEX.matcher(line);
        if (!match.matches()) {
            throw new RuntimeException("Line unmatched: " + line);
        }
        var min = Integer.parseInt(match.group(1));
        var max = Integer.parseInt(match.group(2));
        var ch = match.group(3).charAt(0);
        var password = match.group(4);

        return v.validate(min, max, ch, password);
    }

    @Override
    protected long part1(Stream<String> s) {
        return s.filter(l -> isValid(l, (min, max, ch, password) -> {
            long count = password.chars().filter(c -> c == ch).count();
            return min <= count && count <= max;
        }))
                .count();
    }

    @Override
    protected long part2(Stream<String> s) {
        return s.filter(l -> isValid(l, (min, max, ch, password) ->
                (password.charAt(min - 1) == ch) ^ (password.charAt(max - 1) == ch)))
                .count();
    }
}
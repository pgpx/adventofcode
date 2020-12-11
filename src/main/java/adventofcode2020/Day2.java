package adventofcode2020;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.regex.Pattern;

public class Day2 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        System.out.println("Solving example");
        solve("example-2.txt");

        System.out.println("Solving actual");
        solve("input-2.txt");
    }

    private static final Pattern LINE_REGEX = Pattern.compile("(\\d+)-(\\d+)\\s+(.):\\s+(.*)");

    @FunctionalInterface
    public interface Validator {
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

    public static void solve(String filename) throws IOException, URISyntaxException {
        Validator part1 = (min, max, ch, password) -> {
            long count = password.chars().filter(c -> c == ch).count();
            return min <= count && count <= max;
        };

        System.out.println("Part 1: " + solve(filename, part1));

        Validator part2 = (min, max, ch, password) ->
                (password.charAt(min - 1) == ch) ^ (password.charAt(max - 1) == ch);
        System.out.println("Part 2: " + solve(filename, part2));
    }

    public static long solve(String filename, Validator validator) throws IOException, URISyntaxException {
        try (var s = Util.readFile(Day2.class, filename)) {
            return s.filter(l -> isValid(l, validator)).count();
        }
    }
}
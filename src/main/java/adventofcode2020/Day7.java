package adventofcode2020;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day7 extends Solver {
    public static void main(String[] args) throws URISyntaxException, IOException {
        new Day7().solve("example-7.txt", 4, 32);
        new Day7().solve("input-7.txt", 278, 45157);
    }

    /*(
   dark olive bags contain 3 faded blue bags, 4 dotted black bags.
   bright white bags contain 1 shiny gold bag.
   dotted black bags contain no other bags.
     */
    @Override
    protected long part1(Stream<String> stream) {
        var contains = stream.map(Day7::parseBag).collect(Day7.toTable());

        Deque<String> stack = new ArrayDeque<>();
        stack.addAll(contains.column("shiny gold").keySet());
        Set<String> found = new HashSet<>();

        do {
            String bag = stack.pop();
            if (!found.contains(bag)) {
                found.add(bag);
                stack.addAll(contains.column(bag).keySet());
            }
        } while (!stack.isEmpty());

        return found.size();
    }


    @Override
    protected long part2(Stream<String> stream) {
        var contains = stream.map(Day7::parseBag).collect(Day7.toTable());
        return count(contains, "shiny gold") - 1;    // Don't include the starting bag!
    }

    private static long count(Table<String, String, Integer> contains, String bag) {
        return contains.row(bag).entrySet().stream()
                .mapToLong(e -> e.getValue() * count(contains, e.getKey()))
                .sum() + 1;     // And add this bag
    }

    private static final Pattern NOT_CONTAINS = Pattern.compile("^(.+) bags contain no other bags.$");
    private static final Pattern CONTAINS = Pattern.compile("^(.+) bags contain (.*) bags?\\.$");
    private static final Pattern BAG = Pattern.compile("^(\\d+) (.*)$");

    private static Table<String, String, Integer> parseBag(String line) {
        Matcher m = NOT_CONTAINS.matcher(line);
        if (m.matches()) {
            return HashBasedTable.create();
        }
        m = CONTAINS.matcher(line);
        if (m.matches()) {
            String bag = m.group(1);
            return Stream.of(m.group(2).split(" bags?, "))
                    .map(bagText -> parseContainedBag(bag, bagText))
                    .collect(Day7.toTable());

        }
        throw new RuntimeException("Line unmatched: " + line);
    }

    private static Table<String, String, Integer> parseContainedBag(String bag, String contained) {
        var bagMatcher = BAG.matcher(contained);
        if (bagMatcher.matches()) {
            var r = HashBasedTable.<String, String, Integer>create();
            r.put(bag, bagMatcher.group(2), Integer.valueOf(bagMatcher.group(1)));
            return r;
        }
        throw new RuntimeException("Bag text did not match '" + contained + "' for bag " + bag);
    }

    private static <R, C, V> Collector<Table<R, C, V>, ?, Table<R, C, V>> toTable() {
        return Collectors.reducing(HashBasedTable.create(), (a, b) -> {
            a.putAll(b); return a;
        });
    }

}
package adventofcode2020;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Range;
import com.google.common.collect.Sets;
import com.google.common.primitives.Ints;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day4 extends Solver {
    public static void main(String[] args) throws URISyntaxException, IOException {
        new Day4().start("example-4.txt", "input-4.txt");
    }

    private static final ImmutableSet<String> FIELDS =
            ImmutableSet.copyOf(Arrays.asList("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid", "cid"));

    private static final ImmutableSet<String> OPTIONAL = ImmutableSet.of("cid");
    private static final Set<String> REQUIRED = ImmutableSet.copyOf(Sets.difference(FIELDS, OPTIONAL));

    @Override
    protected long part1(Stream<String> s) {
        return getPassports(s)
                .filter(p -> p.keySet().containsAll(REQUIRED))
                .count();
    }

    private static final Pattern HGT_REGEX = Pattern.compile("^(\\d+)(cm|in)$");
    private static final Pattern HCL_REGEX = Pattern.compile("^#[0-9a-f]{6}$");
    private static final Set<String> ECLS =
            ImmutableSet.copyOf(Arrays.asList("amb", "blu", "brn", "gry", "grn", "hzl", "oth"));
    private static final Pattern PID_REGEX = Pattern.compile("^\\d{9}$");

    @Override
    protected long part2(Stream<String> s) {
        return getPassports(s)
                .filter(p -> p.keySet().containsAll(REQUIRED))
                .filter(p -> p.entrySet().stream().allMatch(Day4::isValid))
                .count();
    }

    private static boolean isValid(Map.Entry<String, String> e) {
        Integer v = Ints.tryParse(e.getValue());
        switch (e.getKey()) {
            case "byr":
                return v != null && Range.closed(1920, 2002).contains(v);
            case "iyr":
                return v != null && Range.closed(2010, 2020).contains(v);
            case "eyr":
                return v != null && Range.closed(2020, 2030).contains(v);
            case "hgt": {
                var match = HGT_REGEX.matcher(e.getValue());
                if (!match.matches()) {
                    return false;
                }
                return ("cm".equals(match.group(2))
                        ? Range.closed(150, 193)
                        : Range.closed(59, 76)
                ).contains(Integer.parseInt(match.group(1)));
            }
            case "hcl":
                return HCL_REGEX.matcher(e.getValue()).matches();
            case "ecl":
                return ECLS.contains(e.getValue());
            case "pid":
                return PID_REGEX.matcher(e.getValue()).matches();
            case "cid":
                return true;
            default:
                return false;
        }
    }

    private Stream<Map<String, String>> getPassports(Stream<String> s) {
        List<Map<String, String>> passports = new ArrayList<>();
        HashMap<String, String> current = null;
        for (var f : s.flatMap(Day4::parseFields).collect(Collectors.toList())) {
            if (f.isEmpty()) {
                if (current != null) {
                    passports.add(current);
                    current = null;
                }
            } else {
                if (current == null) {
                    current = new HashMap<>();
                }
                current.put(f.get().getKey(), f.get().getValue());
            }
        }
        if (current != null) {
            passports.add(current);
        }
        return passports.stream();
    }

    private static Stream<Optional<? extends Map.Entry<String, String>>> parseFields(String s) {
        var tokens = s.split("\\s+");
        return Stream.of(tokens)
                .map(v -> {
                    var i = v.indexOf(":");
                    return i < 0 ? Optional.empty() : Optional.of(
                            new AbstractMap.SimpleImmutableEntry<>(v.substring(0, i), v.substring(i + 1)));
                });
    }
}
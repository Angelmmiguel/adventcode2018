package adventofcode.day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day2 {
    public static void main(String[] args) throws Exception {
        var path = Paths.get(Day2.class.getClassLoader()
                .getResource("input.txt").toURI());


        // PART ONE

        var lines = Files.lines(path);
        var strings = lines.collect(Collectors.toList());
        lines.close();

        AtomicInteger has2 = new AtomicInteger();
        AtomicInteger has3 = new AtomicInteger();

        strings.forEach(id -> {
                    Set<Long> counts = id.chars().boxed().collect(Collectors.toSet())
                            .stream()
                            .map(
                                    uniq -> id.chars().filter(current -> current == uniq).count()
                            )
                            .collect(Collectors.toSet());

                    if (counts.contains(2L)) {
                        has2.accumulateAndGet(1, (x, y) -> x + y);
                    }

                    if (counts.contains(3L)) {
                        has3.accumulateAndGet(1, (x, y) -> x + y);
                    }
                }
        );

        System.out.println("Checksum: " + has2.get() * has3.get());


        // PART TWO

        var checked = new HashSet<String>();

        strings.forEach(currentId -> {
            strings.forEach(compareId -> {
                if (!currentId.equals(compareId) && !checked.contains(compareId)) {
                    var currentChars = currentId.toCharArray();
                    var compareChards = compareId.toCharArray();

                    var charNotEqualsCount = 0;
                    var indexToRemove = -1;

                    for (int i = 0; charNotEqualsCount <= 1 && i < currentChars.length; i++) {
                        if (currentChars[i] != compareChards[i]) {
                            charNotEqualsCount++;
                            indexToRemove = i;
                        }
                    }

                    if (charNotEqualsCount == 1) {
                        System.out.println(currentId);
                        System.out.println(compareId);
                        System.out.println(
                                currentId.substring(0, indexToRemove) +
                                        currentId.substring(indexToRemove + 1)
                        );
                    }
                }
            });

            checked.add(currentId);
        });

    }
}
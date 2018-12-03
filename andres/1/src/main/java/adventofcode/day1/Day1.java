package adventofcode.day1;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;

public class Day1 {
    public static void main(String[] args) throws Exception {
        var path = Paths.get(Day1.class.getClassLoader()
                .getResource("input").toURI());


        // PART ONE

        var lines = Files.lines(path);

        var frequency = lines.map(Integer::parseInt).reduce((c1, c2) -> c1 + c2);
        System.out.println("The current frequency is: " + frequency.get());

        lines.close();

        // PART TWO

        var detectedFrequencies = new HashSet<Integer>();
        var currentFrequency = 0;

        while (!detectedFrequencies.contains(currentFrequency)) {
            var lines2 = Files.lines(path);

            currentFrequency = lines2.map(Integer::parseInt).reduce(currentFrequency,
                    (c1, c2) -> {
                        if (detectedFrequencies.contains(c1)) {
                            return c1;
                        } else {
                            detectedFrequencies.add(c1);
                            return c1 + c2;
                        }
                    }
            );

            lines2.close();
        }

        System.out.println("The current twiceFrequency is: " + currentFrequency);
    }
}

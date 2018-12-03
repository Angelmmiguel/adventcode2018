package adventofcode.day3;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day3 {
    public static void main(String[] args) throws Exception {
        var path = Paths.get(Day3.class.getClassLoader()
                .getResource("input.txt").toURI());

        var SIZE = 1000;

        var fabric = new int[SIZE][SIZE];

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                fabric[i][j] = 0;
            }
        }


        // PART ONE

        var lines = Files.lines(path);
        var strings = lines.collect(Collectors.toList());
        lines.close();

        var pattern = Pattern.compile("#(.*) @ (.*),(.*): (.*)x(.*)");

        var counts = new AtomicInteger();

        var claims = strings.stream()
                .map(claimStr ->
                        {
                            var matcher = pattern.matcher(claimStr);

                            Claim claim = null;
                            while (matcher.find()) {
                                claim = new Claim(
                                        matcher.group(1), matcher.group(3), matcher.group(2),
                                        matcher.group(5), matcher.group(4)
                                );
                            }

                            return claim;
                        }
                )
                .collect(Collectors.toList());

        claims.forEach(claim -> {
            for (int i = 0; i < claim.areaX; i++) {
                for (int j = 0; j < claim.areaY; j++) {
                    if (fabric[claim.startX + i][claim.startY + j] == 0) {
                        fabric[claim.startX + i][claim.startY + j] = 1;
                    } else {
                        if (fabric[claim.startX + i][claim.startY + j] == 1) {
                            counts.accumulateAndGet(1, (x, y) -> x + y);
                        }

                        fabric[claim.startX + i][claim.startY + j] +=
                                fabric[claim.startX + i][claim.startY + j];
                    }
                }
            }
        });

        System.out.println("Square inches of fabric are within two or more claims:" + counts.get());

        claims.forEach(
                claim -> {
                    boolean overlap = false;
                    for (int i = 0; i < claim.areaX; i++) {
                        for (int j = 0; j < claim.areaY; j++) {
                            if (fabric[claim.startX + i][claim.startY + j] != 1) {
                                overlap = true;
                            }
                        }
                    }
                    if (!overlap) System.out.println("The not overlap claim:" + claim.id);
                }
        );
    }
}

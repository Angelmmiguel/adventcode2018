package adventofcode.day5;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day5 {

    public static void main(String[] args) throws Exception {
        var path = Paths.get(Day5.class.getClassLoader()
                .getResource("input.txt").toURI());

        List<String> lowerChars = Arrays.asList(
                "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k",
                "l", "m", "n", "o", "p", "q", "r", "s", "t", "u",
                "v", "w", "x", "y", "z"
        );

        List<String> upperChars = lowerChars.stream().map(String::toUpperCase).collect(Collectors.toList());

        List<String> allMergedChars = new ArrayList<>();

        for (var i = 0; i < lowerChars.size(); i++) {
            allMergedChars.add(lowerChars.get(i) + upperChars.get(i));
            allMergedChars.add(upperChars.get(i) + lowerChars.get(i));
        }

        var lines = Files.lines(path);
        var string = lines.collect(Collectors.toList()).get(0);
        lines.close();

        // PART ONE

        var replaced = "";
        var auxString = string;

        while (!replaced.equals(auxString)) {
            replaced = auxString;
            for (String check : allMergedChars) {
                auxString = auxString.replace(check, "");
            }
        }

        System.out.println(auxString.length());

        // PART TWO

        var minSize = Integer.MAX_VALUE;

        replaced = "";
        auxString = string;

        for (var i = 0; i < lowerChars.size(); i++) {
            auxString = auxString.replace(lowerChars.get(i), "").replace(upperChars.get(i), "");
            while (!replaced.equals(auxString)) {
                replaced = auxString;
                for (String check : allMergedChars) {
                    auxString = auxString.replace(check, "");
                }
            }
            minSize = minSize >= auxString.length() ? auxString.length() : minSize;
            replaced = "";
            auxString = string;
        }

        System.out.println(minSize);

    }
}

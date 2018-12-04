package adventofcode.day4;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day4 {
    public static void main(String[] args) throws Exception {
        var path = Paths.get(Day4.class.getClassLoader()
                .getResource("input.txt").toURI());

        // PART ONE

        var lines = Files.lines(path);
        var strings = lines.collect(Collectors.toList());
        lines.close();

        List<String> sortedEvent = strings.stream().sorted().collect(Collectors.toList());

        var patternShift = Pattern.compile("\\[(.*)\\] Guard #(.*) begins shift");
        var patternStatus = Pattern.compile("\\[(.*)\\]");

        var guards = new HashMap<String, Guard>();
        GuardSession currentGuardSession = null;

        for (var event : sortedEvent) {
            if (event.endsWith("begins shift")) {
                if (currentGuardSession != null) {
                    Guard guard = guards.get(currentGuardSession.id);
                    if (guard == null) guard = new Guard(currentGuardSession.id);
                    guard.addSession(currentGuardSession);
                    guards.put(guard.id, guard);
                }

                var matcher = patternShift.matcher(event);

                while (matcher.find()) {
                    currentGuardSession = new GuardSession(matcher.group(2));
                    currentGuardSession.updateStatus(GuardSession.Status.START, matcher.group(1));
                }

            } else {
                GuardSession.Status status = null;

                if (event.endsWith("asleep")) {
                    status = GuardSession.Status.ASLEEP;
                } else if (event.endsWith("up")) {
                    status = GuardSession.Status.WAKEUP;
                }

                var matcher = patternStatus.matcher(event);

                while (matcher.find()) {
                    currentGuardSession.updateStatus(status, matcher.group(1));
                }
            }
        }

        Guard guard = guards.get(currentGuardSession.id);
        guard.addSession(currentGuardSession);
        guards.put(guard.id, guard);

        // PART 1
        Guard maxSleepGuard = guards.values()
                .stream()
                .reduce((g1, g2) -> {
                    if (g1.totalSleep >= g2.totalSleep) {
                        return g1;
                    } else {
                        return g2;
                    }
                }).get();

        Integer maxTime = Collections.max(maxSleepGuard.totalSleepMinutes);
        System.out.println("Guard: " + maxSleepGuard.id + " minute: " + maxSleepGuard.totalSleepMinutes.indexOf(maxTime));
        System.out.println(Integer.parseInt(maxSleepGuard.id) * maxSleepGuard.totalSleepMinutes.indexOf(maxTime));

        // PART 2

        maxSleepGuard = guards.values()
                .stream()
                .reduce((g1, g2) -> {
                    if (Collections.max(g1.totalSleepMinutes) >= Collections.max(g2.totalSleepMinutes)) {
                        return g1;
                    } else {
                        return g2;
                    }
                }).get();

        maxTime = Collections.max(maxSleepGuard.totalSleepMinutes);

        System.out.println("Guard: " + maxSleepGuard.id + " minute: " + maxSleepGuard.totalSleepMinutes.indexOf(maxTime));
        System.out.println(Integer.parseInt(maxSleepGuard.id) * maxSleepGuard.totalSleepMinutes.indexOf(maxTime));

    }
}

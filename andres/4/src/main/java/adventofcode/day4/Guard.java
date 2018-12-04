package adventofcode.day4;

import java.util.ArrayList;
import java.util.List;

public class Guard {
    public String id;
    public List<GuardSession> sessions = new ArrayList<>();

    public List<Integer> totalSleepMinutes = new ArrayList<>();

    public Integer totalSleep = 0;

    public Guard(String id) {
        this.id = id;
        for (var i = 0; i < 60; i++) totalSleepMinutes.add(i, 0);

    }

    public void addSession(GuardSession session) {
        for (var i = 0; i < 60; i++) totalSleepMinutes.set(i, totalSleepMinutes.get(i) + session.sleepMinutes.get(i));
        totalSleep += session.totalSleep;
        sessions.add(session);
    }
}

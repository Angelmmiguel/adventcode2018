package adventofcode.day4;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.ArrayList;
import java.util.List;

public class GuardSession {
    public enum Status {
        START, ASLEEP, WAKEUP
    }

    public String id;
    public DateTime lastDate;
    public Status lastStatus;

    public List<Integer> sleepMinutes = new ArrayList<>();

    public Integer totalSleep = 0;


    public GuardSession(String id) {
        this.id = id;
        for (var i = 0; i < 60; i++) sleepMinutes.add(i, 0);
    }

    public void updateStatus(Status status, String dateStr) {
        DateTime date = DateTime.parse(dateStr, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm"));

        if (lastStatus != null && lastStatus.equals(Status.ASLEEP)) {

            if (lastDate.getMinuteOfHour() <= date.getMinuteOfHour()) {
                for (var minute = lastDate.getMinuteOfHour() + 1; minute < date.getMinuteOfHour(); minute++) {
                    sleepMinutes.set(minute, sleepMinutes.get(minute) + 1);
                    totalSleep += 1;
                }
            } else {
                for (var minute = lastDate.getMinuteOfHour() + 1; minute < 59; minute++) {
                    sleepMinutes.set(minute, sleepMinutes.get(minute) + 1);
                    totalSleep += 1;
                }

                for (var minute = 0; minute < date.getMinuteOfHour(); minute++) {
                    sleepMinutes.set(minute, sleepMinutes.get(minute) + 1);
                    totalSleep += 1;
                }
            }
        }

        if (status.equals(Status.ASLEEP)) {
            sleepMinutes.set(date.getMinuteOfHour(), sleepMinutes.get(date.getMinuteOfHour()) + 1);
            totalSleep += 1;
        }

        lastDate = date;
        lastStatus = status;
    }
}

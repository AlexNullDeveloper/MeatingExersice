package com.talismanov.beans;

import java.time.LocalTime;

public class WorkingHours {
    private LocalTime from;
    private LocalTime to;

    public WorkingHours(LocalTime from, LocalTime to) {
        this.from = from;
        this.to = to;
    }

    public LocalTime getFrom() {
        return from;
    }

    public LocalTime getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "WorkingHours{" +
                "from=" + from +
                ", to=" + to +
                '}';
    }
}

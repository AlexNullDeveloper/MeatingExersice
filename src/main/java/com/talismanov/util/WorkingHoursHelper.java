package com.talismanov.util;

import com.talismanov.beans.WorkingHours;

import java.time.LocalTime;

public class WorkingHoursHelper {
    public static WorkingHours getWorkingHours(String line) {
        String[] workingHoursArray = line.split(" ");
        String hoursFrom = workingHoursArray[0];
        String hoursTo = workingHoursArray[1];
        LocalTime workingTimeFrom = LocalTime.of(Integer.parseInt(hoursFrom.substring(0, 2)), Integer.parseInt(hoursFrom.substring(2)));
        LocalTime workingTimeTo = LocalTime.of(Integer.parseInt(hoursTo.substring(0, 2)), Integer.parseInt(hoursTo.substring(2)));

        return new WorkingHours(workingTimeFrom, workingTimeTo);
    }
}

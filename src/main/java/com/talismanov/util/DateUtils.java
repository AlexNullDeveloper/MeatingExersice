package com.talismanov.util;

import com.talismanov.beans.Order;
import com.talismanov.beans.WorkingHours;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateUtils {

    static boolean isDatesRangesOverlap(LocalDateTime start1, LocalDateTime end1, LocalDateTime start2, LocalDateTime end2) {

        if (start1 == null || end1 == null || start2 == null || end2 == null) {
            throw new IllegalArgumentException("parameters shouldn't be null");
        }

        if (end1.isBefore(start2) || start1.isAfter(end2) || start1.isEqual(end2) || end1.isEqual(start2)) {
            //they do not overlap
            return false;
        }
        //they overlap
        return true;
    }

    public static boolean isOutsideOfficeHours(Order order, LocalTime orderLocalTime, WorkingHours workingHours) {

        if (order == null || orderLocalTime == null || workingHours == null) {
            throw new IllegalArgumentException("parameters shouldn't be null");
        }

        return orderLocalTime.isBefore(workingHours.getFrom()) ||
                orderLocalTime.plusHours(order.getHoursOfMeeting()).isAfter(workingHours.getTo());
    }
}

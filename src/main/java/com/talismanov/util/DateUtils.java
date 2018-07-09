package com.talismanov.util;

import java.time.LocalDateTime;

public class DateUtils {

    public static boolean isTwoDatesOverlap(LocalDateTime start1, LocalDateTime end1, LocalDateTime start2, LocalDateTime end2) {
        if (end1.isBefore(start2) || start1.isAfter(end2) || start1.isEqual(end2) || end1.isEqual(start2)) {
            //they do not overlap
            return false;
        } else {
            //they overlap
            return true;
        }
    }
}

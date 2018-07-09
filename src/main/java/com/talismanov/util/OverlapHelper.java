package com.talismanov.util;

import com.talismanov.beans.Order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OverlapHelper {

    public static boolean isOverlapWithList(Order order, List<Order> finalList) {
        if (order == null || finalList == null) {
            throw new IllegalArgumentException("arguments shouldn't be null");
        }

        List<Order> copy = new ArrayList<>(finalList);
        return copy.stream()
                .anyMatch(item -> OverlapHelper.isOverlap(order, item));
    }

    static boolean isOverlap(Order order1, Order order2) {
        if (order1 == null || order2 == null) {
            throw new IllegalArgumentException("arguments shouldn't be null");
        }

        LocalDateTime start1 = order1.getOrderedTime();
        LocalDateTime end1 = start1.plusHours(order1.getHoursOfMeeting());

        LocalDateTime start2 = order2.getOrderedTime();
        LocalDateTime end2 = start2.plusHours(order2.getHoursOfMeeting());

        return DateUtils.isDatesRangesOverlap(start1, end1, start2, end2);
    }

}

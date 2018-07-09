package com.talismanov.util;

import com.talismanov.beans.Order;
import com.talismanov.beans.WorkingHours;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DateUtilsTest {

    @Test(expected = IllegalArgumentException.class)
    public void isOutsideOfficeHours() throws Exception {
        DateUtils.isOutsideOfficeHours(null, null, null);
    }

    @Test
    public void isOutsideOfficeHours2() throws Exception {

        Order order = new Order(LocalDateTime.now(), "ADMIN", LocalDateTime.now(), 2);
        LocalDateTime start1 = LocalDateTime.of(1990, 1, 10, 6, 0, 0);
        WorkingHours workingHours = WorkingHoursHelper.getWorkingHours("0700 1730");

        boolean outsideOfficeHours = DateUtils.isOutsideOfficeHours(order, start1.toLocalTime(), workingHours);
        System.out.println(outsideOfficeHours);
        assertTrue(outsideOfficeHours);
    }

    @Test
    public void isOutsideOfficeHours3() throws Exception {

        Order order = new Order(LocalDateTime.now(), "ADMIN", LocalDateTime.now(), 2);
        LocalDateTime start1 = LocalDateTime.of(1990, 1, 10, 8, 0, 0);
        WorkingHours workingHours = WorkingHoursHelper.getWorkingHours("0700 1730");

        boolean outsideOfficeHours = DateUtils.isOutsideOfficeHours(order, start1.toLocalTime(), workingHours);
        System.out.println(outsideOfficeHours);
        assertFalse(outsideOfficeHours);
    }

    @Test
    public void isDatesRangesOverlap1() {
        LocalDateTime start1 = LocalDateTime.of(1990, 1, 10, 6, 0, 0);
        LocalDateTime end1 = LocalDateTime.of(1990, 1, 10, 7, 0, 0);
        LocalDateTime start2 = LocalDateTime.of(1990, 1, 10, 7, 0, 0);
        LocalDateTime end2 = LocalDateTime.of(1990, 1, 10, 8, 0, 0);
        boolean twoDatesOverlap = DateUtils.isDatesRangesOverlap(start1, end1, start2, end2);
        assertFalse(twoDatesOverlap);
    }

    @Test
    public void isDatesRangesOverlap2() {
        LocalDateTime start1 = LocalDateTime.of(1990, 1, 10, 6, 0, 0);
        LocalDateTime end1 = LocalDateTime.of(1990, 1, 10, 7, 30, 0);
        LocalDateTime start2 = LocalDateTime.of(1990, 1, 10, 7, 0, 0);
        LocalDateTime end2 = LocalDateTime.of(1990, 1, 10, 8, 0, 0);
        boolean twoDatesOverlap = DateUtils.isDatesRangesOverlap(start1, end1, start2, end2);
        assertTrue(twoDatesOverlap);
    }

    @Test(expected = IllegalArgumentException.class)
    public void isDatesRangesOverlap3() {
        boolean twoDatesOverlap = DateUtils.isDatesRangesOverlap(null, null, null, null);
        assertTrue(twoDatesOverlap);
    }
}
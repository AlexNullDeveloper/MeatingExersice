package com.talismanov.util;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class DateUtilsTest {

    @Test
    public void isTwoDatesOverlap1() {
        LocalDateTime start1 = LocalDateTime.of(1990, 1, 10, 6, 00, 00);
        LocalDateTime end1 = LocalDateTime.of(1990, 1, 10, 7, 00, 00);
        LocalDateTime start2 = LocalDateTime.of(1990, 1, 10, 7, 00, 00);
        LocalDateTime end2 = LocalDateTime.of(1990, 1, 10, 8, 00, 00);
        boolean twoDatesOverlap = DateUtils.isTwoDatesOverlap(start1, end1, start2, end2);
        assertFalse(twoDatesOverlap);
    }

    @Test
    public void isTwoDatesOverlap2() {
        LocalDateTime start1 = LocalDateTime.of(1990, 1, 10, 6, 00, 00);
        LocalDateTime end1 = LocalDateTime.of(1990, 1, 10, 7, 30, 00);
        LocalDateTime start2 = LocalDateTime.of(1990, 1, 10, 7, 00, 00);
        LocalDateTime end2 = LocalDateTime.of(1990, 1, 10, 8, 00, 00);
        boolean twoDatesOverlap = DateUtils.isTwoDatesOverlap(start1, end1, start2, end2);
        assertTrue(twoDatesOverlap);
    }
}
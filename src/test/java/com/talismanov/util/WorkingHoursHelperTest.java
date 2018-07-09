package com.talismanov.util;

import com.talismanov.beans.WorkingHours;
import org.junit.Test;

import java.time.LocalTime;

import static org.junit.Assert.assertEquals;

public class WorkingHoursHelperTest {
    @Test
    public void getWorkingHours() throws Exception {

        WorkingHours workingHours = WorkingHoursHelper.getWorkingHours("0900 1730");
        LocalTime from = workingHours.getFrom();
        assertEquals(9, from.getHour());
        assertEquals(0, from.getMinute());
        LocalTime to = workingHours.getTo();
        assertEquals(17, to.getHour());
        assertEquals(30, to.getMinute());
    }

}
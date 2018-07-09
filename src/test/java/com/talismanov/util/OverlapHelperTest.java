package com.talismanov.util;

import com.talismanov.beans.Order;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class OverlapHelperTest {
    @Test(expected = IllegalArgumentException.class)
    public void isOverlapWithList() throws Exception {
        OverlapHelper.isOverlapWithList(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void isOverlapWithList2() throws Exception {
        OverlapHelper.isOverlapWithList(new Order(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void isOverlapWithList3() throws Exception {
        OverlapHelper.isOverlapWithList(null, new ArrayList<>());
    }

    @Test
    public void isOverlapWithList4() throws Exception {
        LocalDateTime start1 = LocalDateTime.of(1990, 1, 10, 6, 0, 0);
        Order order = new Order(LocalDateTime.now(), "ADMIN", start1, 2);
        LocalDateTime start2 = LocalDateTime.of(1990, 1, 10, 7, 0, 0);
        Order order2 = new Order(LocalDateTime.now(), "ADMIN", start2, 2);
        List<Order> orders = Collections.singletonList(order2);
        boolean overlap = OverlapHelper.isOverlapWithList(order, orders);
        System.out.println(overlap);
        assertTrue(overlap);
    }

    @Test
    public void isOverlapWithList5() throws Exception {
        LocalDateTime start1 = LocalDateTime.of(1990, 1, 10, 6, 0, 0);
        Order order = new Order(LocalDateTime.now(), "ADMIN", start1, 1);
        LocalDateTime start2 = LocalDateTime.of(1990, 1, 10, 7, 0, 0);
        Order order2 = new Order(LocalDateTime.now(), "ADMIN", start2, 2);
        List<Order> orders = Collections.singletonList(order2);
        boolean overlap = OverlapHelper.isOverlapWithList(order, orders);
        System.out.println(overlap);
        assertFalse(overlap);
    }

    @Test(expected = IllegalArgumentException.class)
    public void isOverlap() throws Exception {
        OverlapHelper.isOverlap(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void isOverlap2() throws Exception {
        OverlapHelper.isOverlap(new Order(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void isOverlap3() throws Exception {
        OverlapHelper.isOverlap(null, new Order());
    }

    @Test
    public void isOverlap4() throws Exception {
        LocalDateTime start1 = LocalDateTime.of(1990, 1, 10, 6, 0, 0);
        Order order = new Order(LocalDateTime.now(), "ADMIN", start1, 2);
        LocalDateTime start2 = LocalDateTime.of(1990, 1, 10, 7, 0, 0);
        Order order2 = new Order(LocalDateTime.now(), "ADMIN", start2, 2);
        boolean overlap = OverlapHelper.isOverlap(order, order2);
        assertEquals(true, overlap);
        System.out.println(overlap);
    }

    @Test
    public void isOverlap5() throws Exception {
        LocalDateTime start1 = LocalDateTime.of(1990, 1, 10, 6, 0, 0);
        Order order = new Order(LocalDateTime.now(), "ADMIN", start1, 1);
        LocalDateTime start2 = LocalDateTime.of(1990, 1, 10, 7, 0, 0);
        Order order2 = new Order(LocalDateTime.now(), "ADMIN", start2, 2);
        boolean overlap = OverlapHelper.isOverlap(order, order2);
        assertEquals(false, overlap);
        System.out.println(overlap);
    }

}
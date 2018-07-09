package com.talismanov.beans;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OrderTest {

    @Test
    public void compareTo() throws Exception {
        Order order1 = new Order(null, null, null, 0);
        Order order2 = new Order(LocalDateTime.now(), "EMP01", LocalDateTime.now(), 2);

        int i = order1.compareTo(order2);
        assertEquals(-1, i);
        System.out.println(i);

        List<Order> orderList = new ArrayList<>();
        orderList.add(order1);
        orderList.add(order2);
        System.out.println(orderList);

        Collections.sort(orderList);

        System.out.println(orderList);

        Order secondElement = orderList.get(1);
        assertTrue(secondElement.getRegisteredAt() == null);

    }

}
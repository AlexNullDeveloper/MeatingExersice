package com.talismanov.beans;

import java.time.LocalDateTime;

public class Order implements Comparable<Order>{
    private LocalDateTime registeredAt;
    private String userRegistered;
    private LocalDateTime orderedTime;
    private int hoursOfMeeting;

    public Order() {

    }

    public Order(LocalDateTime registeredAt, String userRegistered, LocalDateTime orderedTime, int hoursOfMeeting) {
        this.registeredAt = registeredAt;
        this.userRegistered = userRegistered;
        this.orderedTime = orderedTime;
        this.hoursOfMeeting = hoursOfMeeting;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public String getUserRegistered() {
        return userRegistered;
    }

    public LocalDateTime getOrderedTime() {
        return orderedTime;
    }

    public int getHoursOfMeeting() {
        return hoursOfMeeting;
    }

    public void setRegisteredAt(LocalDateTime registeredAt) {
        this.registeredAt = registeredAt;
    }

    public void setUserRegistered(String userRegistered) {
        this.userRegistered = userRegistered;
    }

    public void setOrderedTime(LocalDateTime orderedTime) {
        this.orderedTime = orderedTime;
    }

    public void setHoursOfMeeting(int hoursOfMeeting) {
        this.hoursOfMeeting = hoursOfMeeting;
    }

    @Override
    public String toString() {
        return "Order{" +
                "registeredAt=" + registeredAt +
                ", userRegistered='" + userRegistered + '\'' +
                ", orderedTime=" + orderedTime +
                ", hoursOfMeeting=" + hoursOfMeeting +
                '}';
    }

    /**
     * This is nulls last sorting
     * No NullPointerException will be thrown
     */
    @Override
    public int compareTo(Order o) {

        return this.registeredAt == null || o.getRegisteredAt() == null ? -1 : registeredAt.compareTo(o.getRegisteredAt());
    }
}

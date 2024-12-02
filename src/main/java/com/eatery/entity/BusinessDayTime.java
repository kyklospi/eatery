package com.eatery.entity;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;

/**
 * Record representing the opening and closing times of a business on a specific day.
 * This immutable data structure stores information about the day of the week,
 * opening time, and closing time for a business.
 */
public record BusinessDayTime(DayOfWeek openDay, LocalTime openTime, LocalTime closeTime) {

    /**
     * Overrides the default equals method to compare BusinessDayTime records based on their properties.
     * @param o The object to compare.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BusinessDayTime that)) return false;
        return openDay == that.openDay && Objects.equals(openTime, that.openTime) && Objects.equals(closeTime, that.closeTime);
    }

    /**
     * Overrides the default toString method to provide a readable representation of the BusinessDayTime record.
     * @return A string representation of the BusinessDayTime record.
     */
    @Override
    public String toString() {
        return "BusinessDayTime{" +
                "openDay=" + openDay +
                ", openTime=" + openTime +
                ", closeTime=" + closeTime +
                '}';
    }
}

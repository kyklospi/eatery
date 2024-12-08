package com.eatery.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;

/**
 * Record representing the opening and closing times of a business on a specific day.
 * This immutable data structure stores information about the day of the week,
 * opening time, and closing time for a business.
 */
public record BusinessDayTime(
        DayOfWeek openDay,

        @JsonDeserialize(using = LocalTimeDeserializer.class)
        @JsonSerialize(using = LocalTimeSerializer.class)
        @JsonFormat(pattern = "HH:mm:ss", shape = JsonFormat.Shape.STRING)
        LocalTime openTime,

        @JsonDeserialize(using = LocalTimeDeserializer.class)
        @JsonSerialize(using = LocalTimeSerializer.class)
        @JsonFormat(pattern = "HH:mm:ss", shape = JsonFormat.Shape.STRING)
        LocalTime closeTime
) {

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

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
 * Record representing the start and end times of a business on a specific day.
 * This immutable data structure stores information about the day of the week,
 * opening time & closing time for an eatery and
 * start time & end time for eatery manager work schedule.
 */
public record BusinessDayTime(
        DayOfWeek day,

        @JsonDeserialize(using = LocalTimeDeserializer.class)
        @JsonSerialize(using = LocalTimeSerializer.class)
        @JsonFormat(pattern = "HH:mm:ss", shape = JsonFormat.Shape.STRING)
        LocalTime startTime,

        @JsonDeserialize(using = LocalTimeDeserializer.class)
        @JsonSerialize(using = LocalTimeSerializer.class)
        @JsonFormat(pattern = "HH:mm:ss", shape = JsonFormat.Shape.STRING)
        LocalTime endTime
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
        return day == that.day && Objects.equals(startTime, that.startTime) && Objects.equals(endTime, that.endTime);
    }

    /**
     * Overrides the default toString method to provide a readable representation of the BusinessDayTime record.
     * @return A string representation of the BusinessDayTime record.
     */
    @Override
    public String toString() {
        return "BusinessDayTime{" +
                "day=" + day +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}

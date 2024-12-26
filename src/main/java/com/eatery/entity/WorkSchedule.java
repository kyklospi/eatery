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
 * Record representing the start and end times of a work schedule on a specific day.
 * This immutable data structure stores information about the day of the week,
 * start time, and end time for a work schedule.
 */
public record WorkSchedule(
        DayOfWeek workDay,

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
     * Overrides the default equals method to compare WorkSchedule records based on their properties.
     * @param o The object to compare.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorkSchedule that)) return false;
        return workDay == that.workDay && Objects.equals(startTime, that.startTime) && Objects.equals(endTime, that.endTime);
    }

    /**
     * Overrides the default toString method to provide a readable representation of the WorkSchedule record.
     * @return A string representation of the WorkSchedule record.
     */
    @Override
    public String toString() {
        return "WorkSchedule{" +
                "workDay=" + workDay +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}

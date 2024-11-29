package com.eatery.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BusinessDayTime that)) return false;
        return openDay == that.openDay && Objects.equals(openTime, that.openTime) && Objects.equals(closeTime, that.closeTime);
    }

    @Override
    public String toString() {
        return "BusinessDayTime{" +
                "openDay=" + openDay +
                ", openTime=" + openTime +
                ", closeTime=" + closeTime +
                '}';
    }
}

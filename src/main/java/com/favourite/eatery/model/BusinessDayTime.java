package com.favourite.eatery.model;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;

public record BusinessDayTime(DayOfWeek openDay, LocalTime openTime, LocalTime closeTime) {

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

    public BusinessDayTime {
        if (openTime.isAfter(closeTime)) {
            throw new IllegalArgumentException("Open time must be before close time");
        }
    }
}

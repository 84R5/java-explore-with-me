package ru.practicum.event.enums;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public enum EventSort {
    EVENT_DATE,
    VIEWS,
    RATE;

    public static EventSort by(String stateName) {
        try {
            return EventSort.valueOf(stateName);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown state: " + stateName, e);
        }
    }

    public String getEventSort(EventSort sortVariant) {
        switch (sortVariant) {
            case VIEWS:
                return EventSort.VIEWS.toLowerCamelCase();
            case RATE:
                return EventSort.RATE.toLowerCamelCase();
            case EVENT_DATE:
                return EventSort.EVENT_DATE.toLowerCamelCase();
            default:
                return null;
        }
    }

    private String toLowerCamelCase() {
        String name = name();
        AtomicInteger index = new AtomicInteger();

        return Arrays.stream(name.split("_")).map((value) -> {
            if (index.getAndIncrement() > 0) {
                return value.substring(0, 1).toUpperCase() + value.substring(1).toLowerCase();
            }

            return value.toLowerCase();
        }).collect(Collectors.joining());
    }
}

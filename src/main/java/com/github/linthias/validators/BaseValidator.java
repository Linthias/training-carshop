package com.github.linthias.validators;

import java.time.LocalDate;

public interface BaseValidator <E> {
    boolean isValid(E entity);

    default boolean isBlankString(String input) {
        return input == null || input.isBlank();
    }

    default boolean isValidDate(LocalDate date) {
        return date.isBefore(LocalDate.now());
    }
}

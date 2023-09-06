package com.github.linthias.validators;

import com.github.linthias.dto.CarDto;

public class CarDtoValidator implements BaseValidator<CarDto> {
    @Override
    public boolean isValid(CarDto entity) {
        return !isBlankString(entity.getManufacturer())
                && !isBlankString(entity.getModel())
                && !isBlankString(entity.getColor())
                && isValidDate(entity.getManufactureDate());
    }
}

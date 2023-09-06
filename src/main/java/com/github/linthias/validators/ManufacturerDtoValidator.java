package com.github.linthias.validators;

import com.github.linthias.dto.ManufacturerDto;

public class ManufacturerDtoValidator implements BaseValidator<ManufacturerDto> {
    @Override
    public boolean isValid(ManufacturerDto entity) {
        return !isBlankString(entity.getName());
    }
}

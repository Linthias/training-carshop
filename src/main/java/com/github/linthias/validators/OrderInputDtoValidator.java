package com.github.linthias.validators;

import com.github.linthias.dto.OrderInputDto;

public class OrderInputDtoValidator implements BaseValidator<OrderInputDto> {
    @Override
    public boolean isValid(OrderInputDto entity) {
        return true;
    }
}

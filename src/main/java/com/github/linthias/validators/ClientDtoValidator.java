package com.github.linthias.validators;

import com.github.linthias.dto.ClientDto;

public class ClientDtoValidator implements BaseValidator<ClientDto> {
    @Override
    public boolean isValid(ClientDto entity) {
        return !isBlankString(entity.getFirstName())
                && !isBlankString(entity.getSurname())
                && isValidDate(entity.getBirthdate())
                && (entity.getGender().equals("Male") || entity.getGender().equals("Female"));
    }
}

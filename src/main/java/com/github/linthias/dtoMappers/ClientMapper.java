package com.github.linthias.dtoMappers;

import com.github.linthias.dto.ClientDto;
import com.github.linthias.model.Client;

public class ClientMapper implements BaseMapper <Client, ClientDto>{
    @Override
    public ClientDto toDto(Client entity) {
        return new ClientDto(
                entity.getId(),
                entity.getFirstName(),
                entity.getMiddleName(),
                entity.getSurname(),
                entity.getBirthdate(),
                entity.getGender());
    }

    @Override
    public Client toEntity(ClientDto dto) {
        return new Client(
                dto.getId(),
                dto.getFirstName(),
                dto.getMiddleName(),
                dto.getSurname(),
                dto.getBirthdate(),
                dto.getGender());
    }
}

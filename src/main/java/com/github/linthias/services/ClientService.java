package com.github.linthias.services;

import com.github.linthias.dto.ClientDto;
import com.github.linthias.dtoMappers.BaseMapper;
import com.github.linthias.model.Client;
import com.github.linthias.repositories.BaseRepository;
import com.github.linthias.validators.BaseValidator;

public class ClientService extends BaseService<Client, ClientDto, ClientDto> {
    public ClientService(BaseRepository<Client> entityRepository,
                         BaseValidator<ClientDto> dtoValidator,
                         BaseMapper<Client, ClientDto, ClientDto> dtoMapper) {
        super(entityRepository, dtoValidator, dtoMapper);
    }
}

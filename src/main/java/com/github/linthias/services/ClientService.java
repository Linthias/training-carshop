package com.github.linthias.services;

import com.github.linthias.dto.ClientDto;
import com.github.linthias.dtoMappers.ClientMapper;
import com.github.linthias.repositories.ClientRepository;
import com.github.linthias.validators.ClientDtoValidator;

import java.util.List;
import java.util.stream.Collectors;

public class ClientService implements BaseService<ClientDto> {
    private final ClientRepository clientRepository;
    private final ClientDtoValidator clientDtoValidator;
    private final ClientMapper clientMapper;

    public ClientService(ClientRepository clientRepository, ClientDtoValidator clientDtoValidator, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientDtoValidator = clientDtoValidator;
        this.clientMapper = clientMapper;
    }


    @Override
    public ClientDto add(ClientDto entity) throws Exception {
        if (clientDtoValidator.validate(entity)) {
            entity = clientMapper.toDto(clientRepository.create(clientMapper.toEntity(entity)));
        } else {
            throw new RuntimeException("");
        }

        return entity;
    }

    @Override
    public ClientDto getById(Long id) throws Exception {
        ClientDto clientDto;

        if (clientRepository.exists(id)) {
            clientDto = clientMapper.toDto(clientRepository.findById(id));
        } else {
            throw new RuntimeException("");
        }

        return clientDto;
    }

    @Override
    public List<ClientDto> getAll() throws Exception {
        return clientRepository.findAll().stream().map(clientMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public ClientDto update(ClientDto entity) throws Exception {
        if (clientDtoValidator.validate(entity)) {
            entity = clientMapper.toDto(clientRepository.update(clientMapper.toEntity(entity)));
        } else {
            throw new RuntimeException("");
        }

        return entity;
    }

    @Override
    public boolean deleteById(Long id) throws Exception {
        return clientRepository.deleteById(id);
    }
}

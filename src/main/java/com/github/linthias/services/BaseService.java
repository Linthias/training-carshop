package com.github.linthias.services;

import com.github.linthias.dtoMappers.BaseMapper;
import com.github.linthias.exceptions.DtoNotValidException;
import com.github.linthias.repositories.BaseRepository;
import com.github.linthias.validators.BaseValidator;

import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseService <E, I, O> {
    protected final BaseRepository<E> mainEntityRepository;
    protected final BaseValidator<I> dtoValidator;
    protected final BaseMapper<E, I, O> dtoMapper;

    public BaseService(BaseRepository<E> mainEntityRepository,
                         BaseValidator<I> dtoValidator,
                         BaseMapper<E, I, O> dtoMapper) {
        this.mainEntityRepository = mainEntityRepository;
        this.dtoValidator = dtoValidator;
        this.dtoMapper = dtoMapper;
    }

    protected void validateInput(I input) throws Exception {
        if (!dtoValidator.isValid(input)) {
            throw new DtoNotValidException(input.getClass() + " not valid");
        }
    }

    public O add(I input) throws Exception {
        validateInput(input);

        return dtoMapper.toDto(mainEntityRepository.create(dtoMapper.toEntity(input)));
    }

    public List<O> getById(Long id) throws Exception {
        return List.of(dtoMapper.toDto(mainEntityRepository.findById(id)));
    }

    public List<O> getAll() throws Exception {
        return mainEntityRepository.findAll()
                .stream().map(dtoMapper::toDto).collect(Collectors.toList());
    }

    public O update(I input) throws Exception {
        validateInput(input);

        return dtoMapper.toDto(mainEntityRepository.update(dtoMapper.toEntity(input)));
    }

    public void deleteById(Long id) throws Exception {
        mainEntityRepository.deleteById(id);
    }
}

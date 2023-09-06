package com.github.linthias.dtoMappers;

public interface BaseMapper<E, I, O> {
    O toDto(E entity);
    E toEntity(I input);
}

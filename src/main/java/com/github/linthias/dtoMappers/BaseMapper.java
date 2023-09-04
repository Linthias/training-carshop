package com.github.linthias.dtoMappers;

public interface BaseMapper<E, D> {
    D toDto(E entity);
    E toEntity(D dto);
}

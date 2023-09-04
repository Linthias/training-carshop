package com.github.linthias.services;

import java.util.List;

public interface BaseService <E>{
    E add(E entity) throws Exception;
    E getById(Long id) throws Exception;
    List<E> getAll() throws Exception;
    E update(E entity) throws Exception;
    boolean deleteById(Long id) throws Exception;
}

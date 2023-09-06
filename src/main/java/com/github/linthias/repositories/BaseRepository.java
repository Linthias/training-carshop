package com.github.linthias.repositories;

import com.github.linthias.exceptions.BaseException;
import com.github.linthias.util.DbConnector;

import java.sql.SQLException;
import java.util.List;

public abstract class BaseRepository<E> {
    protected final DbConnector connector;

    protected BaseRepository(DbConnector connector) {
        this.connector = connector;
    }
    public abstract E create(E entity) throws SQLException, BaseException;
    public abstract E findById(Long id) throws SQLException, BaseException;
    public abstract List<E> findAll() throws SQLException;
    public abstract E update(E entity) throws SQLException;
    public abstract void deleteById(Long id) throws SQLException, BaseException;
}

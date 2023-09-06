package com.github.linthias.repositories;

import com.github.linthias.exceptions.BaseException;
import com.github.linthias.exceptions.EntityNotFoundException;
import com.github.linthias.model.Manufacturer;
import com.github.linthias.util.DbConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.github.linthias.sqlStatements.ManufacturerPrepStatement.DELETE_BY_ID;
import static com.github.linthias.sqlStatements.ManufacturerPrepStatement.GET_BY_NAME;
import static com.github.linthias.sqlStatements.ManufacturerPrepStatement.UPDATE_BY_ID;
import static com.github.linthias.sqlStatements.ManufacturerPrepStatement.GET_ALL;
import static com.github.linthias.sqlStatements.ManufacturerPrepStatement.GET_BY_ID;
import static com.github.linthias.sqlStatements.ManufacturerPrepStatement.INSERT;

public class ManufacturerRepository extends BaseRepository<Manufacturer> {
    private static ManufacturerRepository repository;
    protected ManufacturerRepository(DbConnector connector) {
        super(connector);
    }

    public static ManufacturerRepository getInstance(DbConnector connector) {
        if (repository == null) {
            repository = new ManufacturerRepository(connector);
        }
        return repository;
    }

    @Override
    public Manufacturer create(Manufacturer manufacturer) throws SQLException, BaseException {
        try (Connection con = connector.getConnection();
             PreparedStatement stmt = con.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, manufacturer.getId());
            stmt.setString(2, manufacturer.getName());

            stmt.executeUpdate();

            try (ResultSet generatedIds = stmt.getGeneratedKeys()) {
                if (generatedIds.next()) {
                    manufacturer.setId(generatedIds.getLong("manufacturer_id"));
                } else {
                    throw new EntityNotFoundException("id for" + Manufacturer.class + " was not found");
                }
            }
        }

        return manufacturer;
    }

    @Override
    public Manufacturer findById(Long id) throws SQLException, BaseException {
        Manufacturer manufacturer;

        try (Connection con = connector.getConnection();
             PreparedStatement stmt = con.prepareStatement(GET_BY_ID)) {

            stmt.setLong(1, id);

            try (ResultSet result = stmt.executeQuery()) {
                if (result.next()) {
                    manufacturer = new Manufacturer(
                            result.getLong("manufacturer_id"),
                            result.getString("manufacturer_name"));
                } else {
                    throw new EntityNotFoundException(Manufacturer.class + " not found");
                }
            }
        }

        return manufacturer;
    }

    public Manufacturer findByName(String name) throws SQLException, BaseException {
        Manufacturer manufacturer;

        try (Connection con = connector.getConnection();
             PreparedStatement stmt = con.prepareStatement(GET_BY_NAME)) {

            stmt.setString(1, name);

            try (ResultSet result = stmt.executeQuery()) {
                if (result.next()) {
                    manufacturer = new Manufacturer(
                            result.getLong("manufacturer_id"),
                            result.getString("manufacturer_name"));
                } else {
                    throw new EntityNotFoundException(Manufacturer.class + " not found");
                }
            }
        }

        return manufacturer;
    }

    @Override
    public List<Manufacturer> findAll() throws SQLException {
        List<Manufacturer> manufacturers = new ArrayList<>();

        try (Connection con = connector.getConnection();
             PreparedStatement stmt = con.prepareStatement(GET_ALL);
             ResultSet result = stmt.executeQuery()) {

            while (result.next()) {
                manufacturers.add(new Manufacturer(
                        result.getLong("manufacturer_id"),
                        result.getString("manufacturer_name")));
            }

        }

        return manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) throws SQLException {
        try (Connection con = connector.getConnection();
             PreparedStatement stmt = con.prepareStatement(UPDATE_BY_ID)) {

            stmt.setString(1, manufacturer.getName());
            stmt.setLong(2, manufacturer.getId());

            stmt.executeUpdate();
        }

        return null;
    }

    @Override
    public void deleteById(Long id) throws SQLException, BaseException {
        try (Connection con = connector.getConnection();
             PreparedStatement stmt = con.prepareStatement(DELETE_BY_ID)) {

            stmt.setLong(1, id);

            if (stmt.executeUpdate() != 1) {
                throw new EntityNotFoundException(Manufacturer.class + " was not deleted");
            }
        }
    }
}

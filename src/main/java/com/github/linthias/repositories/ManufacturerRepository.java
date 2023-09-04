package com.github.linthias.repositories;

import com.github.linthias.model.Manufacturer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ManufacturerRepository {
    private final String dbUri;
    private final String user;
    private final String password;


    public ManufacturerRepository(String dbUri, String user, String password) {
        this.dbUri = dbUri;
        this.user = user;
        this.password = password;
    }

    public boolean create(Manufacturer manufacturer) {
        int affectedRows;

        try (Connection con = DriverManager.getConnection(dbUri, user, password)) {
            try (Statement stmt = con.createStatement()) {
                String sql =
                        "INSERT INTO "
                                + "PUBLIC.\"manufacturers\" (manufacturer_id, manufacturer_name)"
                                + "VALUES ("
                                + manufacturer.getId() + ", '"
                                + manufacturer.getName() + "')"
                                + " ON CONFLICT DO NOTHING";
                affectedRows = stmt.executeUpdate(sql);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            affectedRows = 0;
        }

        return affectedRows == 1;
    }

    public Manufacturer readById(Long id) {
        Manufacturer manufacturer;

        try (Connection con = DriverManager.getConnection(dbUri, user, password)) {
            try (Statement stmt = con.createStatement()) {
                String sql =
                        "SELECT * FROM PUBLIC.\"manufacturers\" "
                                + "WHERE manufacturer_id = " + id;

                try (ResultSet result = stmt.executeQuery(sql)) {
                    if (result.next()) {
                        manufacturer = new Manufacturer(
                                result.getLong("manufacturer_id"),
                                result.getString("manufacturer_name"));
                    } else {
                        throw new RuntimeException("object not found");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            manufacturer = null;
        }

        return manufacturer;
    }

    public List<Manufacturer> readAll() {
        List<Manufacturer> manufacturers = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(dbUri, user, password)) {
            try (Statement stmt = con.createStatement()) {
                String sql =
                        "SELECT * FROM PUBLIC.\"manufacturers\"";

                try (ResultSet result = stmt.executeQuery(sql)) {
                    while (result.next()) {
                        manufacturers.add(new Manufacturer(
                                result.getLong("manufacturer_id"),
                                result.getString("manufacturer_name")));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            manufacturers = null;
        }
        return manufacturers;
    }

    public boolean update(Manufacturer manufacturer) {
        int affectedRows;

        try (Connection con = DriverManager.getConnection(dbUri, user, password)) {
            try (Statement stmt = con.createStatement()) {
                String sql =
                        "UPDATE PUBLIC.\"manufacturers\" SET "
                                + "manufacturer_name = '" + manufacturer.getName() + "' "
                                + "WHERE manufacturer_id = " + manufacturer.getId();
                affectedRows = stmt.executeUpdate(sql);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            affectedRows = 0;
        }

        return affectedRows == 1;
    }

    public boolean deleteById(Long id) {
        int affectedRows;

        try (Connection con = DriverManager.getConnection(dbUri, user, password)) {
            try (Statement stmt = con.createStatement()) {
                String sql =
                        "DELETE FROM PUBLIC.\"manufacturers\" "
                                + "WHERE manufacturer_id = " + id;
                affectedRows = stmt.executeUpdate(sql);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            affectedRows = 0;
        }

        return affectedRows == 1;
    }
}

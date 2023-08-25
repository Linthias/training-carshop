package com.github.linthias.manufacturers;

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

    public boolean create(ManufacturerModel manufacturer) {
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

    public ManufacturerModel readById(Long id) {
        ManufacturerModel manufacturer;

        try (Connection con = DriverManager.getConnection(dbUri, user, password)) {
            try (Statement stmt = con.createStatement()) {
                String sql =
                        "SELECT * FROM PUBLIC.\"manufacturers\" "
                                + "WHERE manufacturer_id = " + id;

                try (ResultSet result = stmt.executeQuery(sql)) {
                    if (result.next()) {
                        manufacturer = new ManufacturerModel(
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

    public List<ManufacturerModel> readAll() {
        List<ManufacturerModel> manufacturers = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(dbUri, user, password)) {
            try (Statement stmt = con.createStatement()) {
                String sql =
                        "SELECT * FROM PUBLIC.\"manufacturers\"";

                try (ResultSet result = stmt.executeQuery(sql)) {
                    while (result.next()) {
                        manufacturers.add(new ManufacturerModel(
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

    public boolean update(ManufacturerModel manufacturer) {
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

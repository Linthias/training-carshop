package com.github.linthias.clients;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClientRepository {
    private final String dbUri;
    private final String user;
    private final String password;


    public ClientRepository(String dbUri, String user, String password) {
        this.dbUri = dbUri;
        this.user = user;
        this.password = password;
    }

    public boolean create(ClientModel client) {
        int affectedRows;

        try (Connection con = DriverManager.getConnection(dbUri, user, password)) {
            try (Statement stmt = con.createStatement()) {
                String sql =
                        "INSERT INTO "
                                + "PUBLIC.\"clients\" (client_id, first_name, middle_name, surname, birthdate, gender)"
                                + "VALUES "
                                + "(DEFAULT, '"
                                + client.getFirstName() + "', '"
                                + client.getMiddleName() + "', '"
                                + client.getSurname() + "', '"
                                + client.getBirthdate().toString() + "', '"
                                + client.getGender() + "')"
                                + " ON CONFLICT DO NOTHING";
                affectedRows = stmt.executeUpdate(sql);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            affectedRows = 0;
        }

        return affectedRows == 1;
    }

    public ClientModel readById(Long id) {
        ClientModel client;
        try (Connection con = DriverManager.getConnection(dbUri, user, password)) {
            try (Statement stmt = con.createStatement()) {
                String sql =
                        "SELECT * FROM PUBLIC.\"clients\" "
                                + "WHERE client_id = " + id;

                try (ResultSet result = stmt.executeQuery(sql)) {
                    if (result.next()) {
                        client = new ClientModel(
                                result.getLong("client_id"),
                                result.getString("first_name"),
                                result.getString("middle_name"),
                                result.getString("surname"),
                                result.getDate("birthdate").toLocalDate(),
                                result.getString("gender"));
                    } else {
                        throw new RuntimeException("object not found");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            client = null;
        }

        return client;
    }

    public List<ClientModel> readAll() {
        List<ClientModel> clients = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(dbUri, user, password)) {
            try (Statement stmt = con.createStatement()) {
                String sql =
                        "SELECT * FROM PUBLIC.\"clients\"";

                try (ResultSet result = stmt.executeQuery(sql)) {
                    while (result.next()) {
                        clients.add(new ClientModel(
                                result.getLong("client_id"),
                                result.getString("first_name"),
                                result.getString("middle_name"),
                                result.getString("surname"),
                                result.getDate("birthdate").toLocalDate(),
                                result.getString("gender")));
                    }
                }

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            clients = null;
        }
        return clients;
    }

    public boolean update(ClientModel client) {
        int affectedRows;

        try (Connection con = DriverManager.getConnection(dbUri, user, password)) {
            try (Statement stmt = con.createStatement()) {
                String sql =
                        "UPDATE PUBLIC.\"clients\" SET "
                                + "first_name = '" + client.getFirstName() + "', "
                                + "middle_name = '" + client.getMiddleName() + "', "
                                + "surname = '" + client.getSurname() + "', "
                                + "birthdate = '" + client.getBirthdate().toString() + "', "
                                + "gender = '" + client.getGender() + "' "
                                + "WHERE client_id = " + client.getId();
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
                        "DELETE FROM PUBLIC.\"clients\" "
                                + "WHERE client_id = " + id;
                affectedRows = stmt.executeUpdate(sql);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            affectedRows = 0;
        }

        return affectedRows == 1;
    }
}

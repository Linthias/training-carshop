package com.github.linthias.repositories;

import com.github.linthias.model.Client;
import com.github.linthias.util.DbConnector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.github.linthias.sqlStatements.ClientPrepStatement.COUNT_BY_ID;
import static com.github.linthias.sqlStatements.ClientPrepStatement.DELETE_BY_ID;
import static com.github.linthias.sqlStatements.ClientPrepStatement.GET_ALL;
import static com.github.linthias.sqlStatements.ClientPrepStatement.GET_BY_ID;
import static com.github.linthias.sqlStatements.ClientPrepStatement.INSERT;
import static com.github.linthias.sqlStatements.ClientPrepStatement.UPDATE_BY_ID;

public class ClientRepository {
    private final DbConnector connector;

    public ClientRepository(DbConnector connector) {
        this.connector = connector;
    }

    public Client create(Client client) throws SQLException {
        try (Connection con = connector.getConnection();
             PreparedStatement stmt = con.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, client.getFirstName());
            stmt.setString(2, client.getMiddleName());
            stmt.setString(3, client.getSurname());
            stmt.setString(4, client.getBirthdate().toString());
            stmt.setString(5, client.getGender());

            stmt.executeUpdate();

            try (ResultSet generatedIds = stmt.getGeneratedKeys()) {
                if (generatedIds.next()) {
                    client.setId(generatedIds.getLong("client_id"));
                } else {
                    throw new RuntimeException("id not found");
                }
            }
        }

        return client;
    }

    public Client findById(Long id) throws SQLException {
        Client client = null;

        try (Connection con = connector.getConnection();
             PreparedStatement stmt = con.prepareStatement(GET_BY_ID)) {

            stmt.setLong(1, id);

            try (ResultSet result = stmt.executeQuery()) {
                if (result.next()) {
                    client = new Client(
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

        return client;
    }

    public List<Client> findAll() throws SQLException {
        List<Client> clients = new ArrayList<>();

        try (Connection con = connector.getConnection();
             PreparedStatement stmt = con.prepareStatement(GET_ALL);
             ResultSet result = stmt.executeQuery()) {

                while (result.next()) {
                    clients.add(new Client(
                            result.getLong("client_id"),
                            result.getString("first_name"),
                            result.getString("middle_name"),
                            result.getString("surname"),
                            result.getDate("birthdate").toLocalDate(),
                            result.getString("gender")));
                }

        }

        return clients;
    }

    public Client update(Client client) throws SQLException {

        try (Connection con = connector.getConnection();
             PreparedStatement stmt = con.prepareStatement(UPDATE_BY_ID)) {

            stmt.setString(1, client.getFirstName());
            stmt.setString(2, client.getMiddleName());
            stmt.setString(3, client.getSurname());
            stmt.setString(4, client.getBirthdate().toString());
            stmt.setString(5, client.getGender());
            stmt.setLong(6, client.getId());

            stmt.executeUpdate();
        }

        return client;
    }

    public boolean deleteById(Long id) throws SQLException {
        int affectedRows;

        try (Connection con = connector.getConnection();
             PreparedStatement stmt = con.prepareStatement(DELETE_BY_ID)) {

            stmt.setLong(1, id);

            affectedRows = stmt.executeUpdate();
        }

        return affectedRows == 1;
    }

    public boolean exists(Long id) throws SQLException {
        long count;

        try (Connection con = connector.getConnection();
             PreparedStatement stmt = con.prepareStatement(COUNT_BY_ID)) {

            stmt.setLong(1, id);

            try (ResultSet result = stmt.executeQuery()) {
                if (result.next()) {
                    count = result.getLong("count");
                } else {
                    throw new RuntimeException("object not found");
                }
            }
        }

        return count == 1;
    }
}

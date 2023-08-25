package com.github.linthias.orders;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository {
    private final String dbUri;
    private final String user;
    private final String password;


    public OrderRepository(String dbUri, String user, String password) {
        this.dbUri = dbUri;
        this.user = user;
        this.password = password;
    }

    public boolean create(OrderModel order) {
        int affectedRows;

        try (Connection con = DriverManager.getConnection(dbUri, user, password)) {
            try (Statement stmt = con.createStatement()) {
                String sql =
                        "INSERT INTO "
                                + "PUBLIC.\"orders\" (order_id, client_id, car_id, order_date)"
                                + "VALUES "
                                + "(DEFAULT, "
                                + order.getClientId() + ", "
                                + order.getCarId() + ", '"
                                + order.getOrderDate().toString() + "')"
                                + " ON CONFLICT DO NOTHING";
                affectedRows = stmt.executeUpdate(sql);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            affectedRows = 0;
        }

        return affectedRows == 1;
    }

    public OrderModel readById(Long id) {
        OrderModel order;

        try (Connection con = DriverManager.getConnection(dbUri, user, password)) {
            try (Statement stmt = con.createStatement()) {
                String sql =
                        "SELECT * FROM PUBLIC.\"orders\" "
                                + "WHERE order_id = " + id;

                try (ResultSet result = stmt.executeQuery(sql)) {
                    if (result.next()) {
                        order = new OrderModel(
                                result.getLong("order_id"),
                                result.getLong("client_id"),
                                result.getLong("car_id"),
                                result.getDate("order_date").toLocalDate());
                    } else {
                        throw new RuntimeException("object not found");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            order = null;
        }

        return order;
    }

    public List<OrderModel> readAll() {
        List<OrderModel> orders = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(dbUri, user, password)) {
            try (Statement stmt = con.createStatement()) {
                String sql =
                        "SELECT * FROM PUBLIC.\"orders\"";

                try (ResultSet result = stmt.executeQuery(sql)) {
                    while (result.next()) {
                        orders.add(new OrderModel(
                                result.getLong("order_id"),
                                result.getLong("client_id"),
                                result.getLong("car_id"),
                                result.getDate("order_date").toLocalDate()));
                    }
                }

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            orders = null;
        }
        return orders;
    }

    public boolean update(OrderModel order) {
        int affectedRows;

        try (Connection con = DriverManager.getConnection(dbUri, user, password)) {
            try (Statement stmt = con.createStatement()) {
                String sql =
                        "UPDATE PUBLIC.\"orders\" SET "
                                + "client_id = " + order.getClientId() + ", "
                                + "car_id = " + order.getCarId() + ", "
                                + "order_date = '" + order.getOrderDate().toString() + "' "
                                + "WHERE order_id = " + order.getId();
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
                        "DELETE FROM PUBLIC.\"orders\" "
                                + "WHERE order_id = " + id;
                affectedRows = stmt.executeUpdate(sql);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            affectedRows = 0;
        }

        return affectedRows == 1;
    }
}

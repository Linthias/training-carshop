package com.github.linthias.repositories;

import com.github.linthias.exceptions.BaseException;
import com.github.linthias.exceptions.EntityNotFoundException;
import com.github.linthias.model.Order;
import com.github.linthias.util.DbConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.github.linthias.sqlStatements.OrderPrepStatement.DELETE_BY_ID;
import static com.github.linthias.sqlStatements.OrderPrepStatement.UPDATE_BY_ID;
import static com.github.linthias.sqlStatements.OrderPrepStatement.GET_ALL;
import static com.github.linthias.sqlStatements.OrderPrepStatement.GET_BY_ID;
import static com.github.linthias.sqlStatements.OrderPrepStatement.INSERT;

public class OrderRepository extends BaseRepository<Order> {
    private static OrderRepository repository;

    protected OrderRepository(DbConnector connector) {
        super(connector);
    }

    public static OrderRepository getInstance(DbConnector connector) {
        if (repository == null) {
            repository = new OrderRepository(connector);
        }

        return repository;
    }

    @Override
    public Order create(Order order) throws SQLException, BaseException {
        try (Connection con = connector.getConnection();
             PreparedStatement stmt = con.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, order.getClientId());
            stmt.setLong(2, order.getCarId());
            stmt.setString(3, order.getOrderDate().toString());

            stmt.executeUpdate();

            try (ResultSet generatedIds = stmt.getGeneratedKeys()) {
                if (generatedIds.next()) {
                    order.setId(generatedIds.getLong("order_id"));
                } else {
                    throw new EntityNotFoundException("id for" + Order.class + " was not found");
                }
            }
        }

        return order;
    }

    @Override
    public Order findById(Long id) throws SQLException, BaseException {
        Order order;

        try (Connection con = connector.getConnection();
             PreparedStatement stmt = con.prepareStatement(GET_BY_ID)) {

            stmt.setLong(1, id);

            try (ResultSet result = stmt.executeQuery()) {
                if (result.next()) {
                    order = new Order(result.getLong("order_id"),
                            result.getLong("client_id"),
                            result.getLong("car_id"),
                            result.getDate("order_date").toLocalDate());
                } else {
                    throw new EntityNotFoundException(Order.class + " not found");
                }
            }
        }

        return order;
    }

    @Override
    public List<Order> findAll() throws SQLException {
        List<Order> orders = new ArrayList<>();

        try (Connection con = connector.getConnection();
             PreparedStatement stmt = con.prepareStatement(GET_ALL);
             ResultSet result = stmt.executeQuery()) {

            while (result.next()) {
                orders.add(new Order(result.getLong("order_id"),
                        result.getLong("client_id"),
                        result.getLong("car_id"),
                        result.getDate("order_date").toLocalDate()));
            }
        }

        return orders;
    }

    @Override
    public Order update(Order order) throws SQLException {
        try (Connection con = connector.getConnection();
             PreparedStatement stmt = con.prepareStatement(UPDATE_BY_ID)) {

            stmt.setLong(1, order.getClientId());
            stmt.setLong(2, order.getCarId());
            stmt.setString(3, order.getOrderDate().toString());
            stmt.setLong(4, order.getId());

            stmt.executeUpdate();
        }

        return order;
    }

    @Override
    public void deleteById(Long id) throws SQLException, BaseException {
        try (Connection con = connector.getConnection();
             PreparedStatement stmt = con.prepareStatement(DELETE_BY_ID)) {

            stmt.setLong(1, id);

            if (stmt.executeUpdate() != 1) {
                throw new EntityNotFoundException(Order.class + " was not deleted");
            }
        }
    }
}

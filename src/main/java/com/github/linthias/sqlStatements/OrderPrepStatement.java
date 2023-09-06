package com.github.linthias.sqlStatements;

public class OrderPrepStatement {
    private OrderPrepStatement() {};

    public final static String INSERT = "INSERT INTO orders "
            + "(order_id, client_id, car_id, order_date) "
            + "VALUES (DEFAULT, ?, ?, ?) "
            + "ON CONFLICT DO NOTHING";

    public final static String GET_ALL = "SELECT * FROM orders";

    public final static String GET_BY_ID = GET_ALL + " WHERE order_id = ?";

    public final static String UPDATE_BY_ID = "UPDATE orders SET "
            + "client_id = ?, "
            + "car_id = ?, "
            + "order_date = ? "
            + "WHERE order_id = ?";

    public final static String DELETE_BY_ID = "DELETE FROM orders WHERE order_id = ?";
}

package com.github.linthias.sqlStatements;

public class CarPrepStatement {
    private CarPrepStatement() {};

    public final static String INSERT = "INSERT INTO cars "
            + "(car_id, manufacturer_id, car_model, color, engine_displacement, manufacture_date, price) "
            + "VALUES (DEFAULT, ?, ?, ?, ?, ?, ?) "
            + "ON CONFLICT DO NOTHING";

    public final static String GET_ALL = "SELECT * FROM cars";

    public final static String GET_BY_ID = GET_ALL + " WHERE car_id = ?";

    public final static String UPDATE_BY_ID = "UPDATE cars SET "
            + "manufacturer_id = ?, "
            + "car_model = ?, "
            + "color = ?, "
            + "engine_displacement = ?, "
            + "manufacture_date = ?, "
            + "price = ? "
            + "WHERE car_id = ?";

    public final static String DELETE_BY_ID = "DELETE FROM cars WHERE car_id = ?";
}

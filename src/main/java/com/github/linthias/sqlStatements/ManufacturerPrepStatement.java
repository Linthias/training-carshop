package com.github.linthias.sqlStatements;

public class ManufacturerPrepStatement {
    private ManufacturerPrepStatement() {};

    public final static String INSERT = "INSERT INTO manufacturers "
            + "(manufacturer_id, manufacturer_name) "
            + "VALUES (?, ?) "
            + "ON CONFLICT DO NOTHING";

    public final static String GET_ALL = "SELECT * FROM manufacturers";

    public final static String GET_BY_ID = GET_ALL + " WHERE manufacturer_id = ?";

    public final static String GET_BY_NAME = GET_ALL + " WHERE manufacturer_name = ?";

    public final static String UPDATE_BY_ID = "UPDATE manufacturers SET "
            + "manufacturer_name = ? "
            + "WHERE manufacturer_id = ?";

    public final static String DELETE_BY_ID = "DELETE FROM manufacturers WHERE manufacturer_id = ?";
}

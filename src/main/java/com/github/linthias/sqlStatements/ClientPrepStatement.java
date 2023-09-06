package com.github.linthias.sqlStatements;

public class ClientPrepStatement {
    private ClientPrepStatement() {};

    public final static String INSERT = "INSERT INTO clients "
            + "(client_id, first_name, middle_name, surname, birthdate, gender) "
            + "VALUES (DEFAULT, ?, ?, ?, ?, ?) "
            + "ON CONFLICT DO NOTHING";

    public final static String GET_ALL = "SELECT * FROM clients";

    public final static String GET_BY_ID = GET_ALL + " WHERE client_id = ?";

    public final static String UPDATE_BY_ID = "UPDATE clients SET "
            + "first_name = ?, "
            + "middle_name = ?, "
            + "surname = ?, "
            + "birthdate = ?, "
            + "gender = ? "
            + "WHERE client_id = ?";

    public final static String DELETE_BY_ID = "DELETE FROM clients WHERE client_id = ?";
}

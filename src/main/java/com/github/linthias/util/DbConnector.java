package com.github.linthias.util;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DbConnector {
    private final DataSource dataSource;

    public DbConnector(DataSource dataSource) {
        this.dataSource = dataSource;

    }

    public Connection getConnection(){
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

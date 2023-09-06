package com.github.linthias.repositories;

import com.github.linthias.exceptions.BaseException;
import com.github.linthias.exceptions.EntityNotFoundException;
import com.github.linthias.model.Car;
import com.github.linthias.util.DbConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.github.linthias.sqlStatements.CarPrepStatement.INSERT;
import static com.github.linthias.sqlStatements.CarPrepStatement.GET_BY_ID;
import static com.github.linthias.sqlStatements.CarPrepStatement.GET_ALL;
import static com.github.linthias.sqlStatements.CarPrepStatement.UPDATE_BY_ID;
import static com.github.linthias.sqlStatements.CarPrepStatement.DELETE_BY_ID;

public class CarRepository extends BaseRepository<Car> {
    private static CarRepository repository;
    protected CarRepository(DbConnector connector) {
        super(connector);
    }

    public static CarRepository getInstance(DbConnector connector) {
        if (repository == null) {
            repository = new CarRepository(connector);
        }

        return repository;
    }

    @Override
    public Car create(Car car) throws SQLException, BaseException {
        try (Connection con = connector.getConnection();
             PreparedStatement stmt = con.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, car.getManufacturerId());
            stmt.setString(2, car.getModel());
            stmt.setString(3, car.getColor());
            stmt.setDouble(4, car.getEngineDisplacement());
            stmt.setString(5, car.getManufactureDate().toString());
            stmt.setDouble(6, car.getPrice().doubleValue());

            stmt.executeUpdate();

            try (ResultSet generatedIds = stmt.getGeneratedKeys()) {
                if (generatedIds.next()) {
                    car.setId(generatedIds.getLong("car_id"));
                } else {
                    throw new EntityNotFoundException("id for" + Car.class + " was not found");
                }
            }
        }

        return car;
    }

    @Override
    public Car findById(Long id) throws SQLException, BaseException {
        Car car;

        try (Connection con = connector.getConnection();
             PreparedStatement stmt = con.prepareStatement(GET_BY_ID)) {

            stmt.setLong(1, id);

            try (ResultSet result = stmt.executeQuery()) {
                if (result.next()) {
                    car = new Car(
                            result.getLong("car_id"),
                            result.getLong("manufacturer_id"),
                            result.getString("car_model"),
                            result.getString("color"),
                            result.getDouble("engine_displacement"),
                            result.getDate("manufacture_date").toLocalDate(),
                            result.getBigDecimal("price"));
                } else {
                    throw new EntityNotFoundException(Car.class + " not found");
                }
            }
        }

        return car;
    }

    @Override
    public List<Car> findAll() throws SQLException {
        List<Car> cars = new ArrayList<>();

        try (Connection con = connector.getConnection();
             PreparedStatement stmt = con.prepareStatement(GET_ALL);
             ResultSet result = stmt.executeQuery()) {

            while (result.next()) {
                cars.add(new Car(
                        result.getLong("car_id"),
                        result.getLong("manufacturer_id"),
                        result.getString("car_model"),
                        result.getString("color"),
                        result.getDouble("engine_displacement"),
                        result.getDate("manufacture_date").toLocalDate(),
                        result.getBigDecimal("price")));
            }
        }

        return cars;
    }

    @Override
    public Car update(Car car) throws SQLException {
        try (Connection con = connector.getConnection();
             PreparedStatement stmt = con.prepareStatement(UPDATE_BY_ID)) {

            stmt.setLong(1, car.getManufacturerId());
            stmt.setString(2, car.getModel());
            stmt.setString(3, car.getColor());
            stmt.setDouble(4, car.getEngineDisplacement());
            stmt.setString(5, car.getManufactureDate().toString());
            stmt.setDouble(6, car.getPrice().doubleValue());
            stmt.setLong(7, car.getId());

            stmt.executeUpdate();
        }

        return car;
    }

    @Override
    public void deleteById(Long id) throws SQLException, BaseException {
        try (Connection con = connector.getConnection();
             PreparedStatement stmt = con.prepareStatement(DELETE_BY_ID)) {

            stmt.setLong(1, id);

            if (stmt.executeUpdate() != 1) {
                throw new EntityNotFoundException(Car.class + " was not deleted");
            }
        }
    }
}

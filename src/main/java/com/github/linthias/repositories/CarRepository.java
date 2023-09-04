package com.github.linthias.repositories;

import com.github.linthias.model.Car;
import com.github.linthias.model.Manufacturer;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CarRepository {
    private final String dbUri;
    private final String user;
    private final String password;
    private final ManufacturerRepository manufacturerRepository;


    public CarRepository(String dbUri, String user, String password) {
        this.dbUri = dbUri;
        this.user = user;
        this.password = password;
        this.manufacturerRepository = new ManufacturerRepository(dbUri, user, password);
    }

    public boolean create(Car car) {
        int affectedRows;
        long manufacturerId = -1;

        List<Manufacturer> manufacturers = manufacturerRepository.readAll();
        for (Manufacturer manufacturer : manufacturers) {
            if (manufacturer.getName().equals(car.getManufacturer())) {
                manufacturerId = manufacturer.getId();
                break;
            }
        }

        try (Connection con = DriverManager.getConnection(dbUri, user, password)) {
            try (Statement stmt = con.createStatement()) {
                String sql =
                        "INSERT INTO "
                                + "PUBLIC.\"cars\" (car_id, manufacturer_id, car_model, color, "
                                + "engine_displacement, manufacture_date, price)"
                                + "VALUES "
                                + "(DEFAULT, "
                                + manufacturerId + ", '"
                                + car.getModel() + "', '"
                                + car.getColor() + "', "
                                + car.getEngineDisplacement() + ", '"
                                + car.getManufactureDate().toString() + "', "
                                + car.getPrice().doubleValue() + ")"
                                + " ON CONFLICT DO NOTHING";
                affectedRows = stmt.executeUpdate(sql);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            affectedRows = 0;
        }

        return affectedRows == 1;
    }

    public Car readById(Long id) {
        Car car;

        try (Connection con = DriverManager.getConnection(dbUri, user, password)) {
            try (Statement stmt = con.createStatement()) {
                String sql =
                        "SELECT * FROM PUBLIC.\"cars\" "
                                + "JOIN PUBLIC.\"manufacturers\" "
                                + "ON PUBLIC.\"cars\".manufacturer_id = PUBLIC.\"manufacturers\".manufacturer_id "
                                + "WHERE car_id = " + id;

                try (ResultSet result = stmt.executeQuery(sql)) {
                    if (result.next()) {
                        car = new Car(
                                result.getLong("car_id"),
                                result.getString("manufacturer_name"),
                                result.getString("car_model"),
                                result.getString("color"),
                                result.getDouble("engine_displacement"),
                                result.getDate("manufacture_date").toLocalDate(),
                                BigDecimal.valueOf(result.getDouble("price")));
                    } else {
                        throw new RuntimeException("object not found");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            car = null;
        }

        return car;
    }

    public List<Car> readAll() {
        List<Car> cars = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(dbUri, user, password)) {
            try (Statement stmt = con.createStatement()) {
                String sql =
                        "SELECT * FROM PUBLIC.\"cars\" "
                                + "JOIN PUBLIC.\"manufacturers\" "
                                + "ON PUBLIC.\"cars\".manufacturer_id = PUBLIC.\"manufacturers\".manufacturer_id";

                try (ResultSet result = stmt.executeQuery(sql)) {
                    while (result.next()) {
                        cars.add(new Car(
                                result.getLong("car_id"),
                                result.getString("manufacturer_name"),
                                result.getString("car_model"),
                                result.getString("color"),
                                result.getDouble("engine_displacement"),
                                result.getDate("manufacture_date").toLocalDate(),
                                BigDecimal.valueOf(result.getDouble("price"))));
                    }
                }

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            cars = null;
        }
        return cars;
    }

    public boolean update(Car car) {
        int affectedRows;
        long manufacturerId = -1;

        List<Manufacturer> manufacturers = manufacturerRepository.readAll();
        for (Manufacturer manufacturer : manufacturers) {
            if (manufacturer.getName().equals(car.getManufacturer())) {
                manufacturerId = manufacturer.getId();
                break;
            }
        }

        try (Connection con = DriverManager.getConnection(dbUri, user, password)) {
            try (Statement stmt = con.createStatement()) {
                String sql =
                        "UPDATE PUBLIC.\"cars\" SET "
                                + "manufacturer_id = " + manufacturerId + ", "
                                + "car_model = '" + car.getModel() + "', "
                                + "color = '" + car.getColor() + "', "
                                + "engine_displacement = " + car.getEngineDisplacement() + ", "
                                + "manufacture_date = '" + car.getManufactureDate().toString() + "', "
                                + "price = " + car.getPrice().doubleValue() + " "
                                + "WHERE car_id = " + car.getId();
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
                        "DELETE FROM PUBLIC.\"cars\" "
                                + "WHERE car_id = " + id;
                affectedRows = stmt.executeUpdate(sql);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            affectedRows = 0;
        }

        return affectedRows == 1;
    }
}

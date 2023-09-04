package com.github.linthias.controllers;

import com.github.linthias.model.Car;
import com.github.linthias.repositories.CarRepository;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.postgresql.Driver;

import java.io.PrintWriter;
import java.sql.DriverManager;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/cars")
public class CarController extends HttpServlet {
    private CarRepository carRepository;
    private Gson gson;
    private Driver dbDriver;

    @Override
    public void init() {
        gson = new Gson();
        dbDriver = new Driver();
        carRepository = new CarRepository(
                getServletContext().getInitParameter("dbAddress"),
                getServletContext().getInitParameter("dbUser"),
                getServletContext().getInitParameter("dbPassword"));
    }

    private long getIdParam(HttpServletRequest request) {
        long id = -1;
        Enumeration<String> parameterNames = request.getParameterNames();

        while (parameterNames.hasMoreElements()) {
            String name = parameterNames.nextElement();
            if (name.equals("id")) {
                id = Long.parseLong(request.getParameter(name));
                break;
            }
        }

        return id;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            try {
                String body = request.getReader().lines().collect(Collectors.joining("\n"));

                Car newCar = gson.fromJson(body, Car.class);

                DriverManager.registerDriver(dbDriver);

                if (carRepository.create(newCar)) {
                    response.setStatus(201);
                } else {
                    response.sendError(500);
                }
            } finally {
                DriverManager.deregisterDriver(dbDriver);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        Car car;
        List<Car> cars;
        String output;

        try {
            try {
                long id = getIdParam(request);

                DriverManager.registerDriver(dbDriver);

                if (id == -1) {
                    cars = carRepository.readAll();
                    if (cars == null) {
                        response.sendError(404);
                        throw new RuntimeException("not found");
                    }

                    output = gson.toJson(cars);
                } else {
                    car = carRepository.readById(id);
                    if (car == null) {
                        response.sendError(404);
                        throw new RuntimeException("not found");
                    }

                    output = gson.toJson(car);
                }

                response.setStatus(200);
                PrintWriter writer = response.getWriter();
                writer.println(output);
            } finally {
                DriverManager.deregisterDriver(dbDriver);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {
        try {
            try {
                String body = request.getReader().lines().collect(Collectors.joining("\n"));

                Car newCar = gson.fromJson(body, Car.class);

                DriverManager.registerDriver(dbDriver);

                if (carRepository.update(newCar)) {
                    response.setStatus(200);
                } else {
                    response.sendError(500);
                }
            } finally {
                DriverManager.deregisterDriver(dbDriver);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        try {
            try {
                long id = getIdParam(request);
                if (id == -1) {
                    throw new RuntimeException("id not found in parameter list");
                }

                DriverManager.registerDriver(dbDriver);

                if (carRepository.deleteById(id)) {
                    response.setStatus(200);
                } else {
                    response.sendError(500);
                }
            } finally {
                DriverManager.deregisterDriver(dbDriver);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

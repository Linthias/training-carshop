package com.github.linthias.controllers;

import com.github.linthias.dto.CarDto;
import com.github.linthias.model.Car;
import com.github.linthias.services.CarService;
import com.github.linthias.util.ExceptionHandler;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet("/cars/*")
public class CarController extends BaseController<Car, CarDto, CarDto> {
    @Override
    public void init() {
        gson = new Gson();
        exceptionHandler = ExceptionHandler.getInstance();
        service = (CarService) getServletContext().getAttribute("carService");
    }

    @Override
    protected CarDto parseBody(HttpServletRequest request) throws IOException {
        return gson.fromJson(
                request.getReader().lines().collect(Collectors.joining("\n")),
                CarDto.class);
    }
}

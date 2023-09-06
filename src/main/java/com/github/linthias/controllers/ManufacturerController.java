package com.github.linthias.controllers;

import com.github.linthias.dto.ManufacturerDto;
import com.github.linthias.model.Manufacturer;
import com.github.linthias.services.ManufacturerService;
import com.github.linthias.util.ExceptionHandler;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet("/manufacturers/*")
public class ManufacturerController extends BaseController<Manufacturer, ManufacturerDto, ManufacturerDto> {
    @Override
    public void init() {
        gson = new Gson();
        exceptionHandler = ExceptionHandler.getInstance();
        service = (ManufacturerService) getServletContext().getAttribute("manufacturerService");
    }

    @Override
    protected ManufacturerDto parseBody(HttpServletRequest request) throws IOException {
        return gson.fromJson(
                request.getReader().lines().collect(Collectors.joining("\n")),
                ManufacturerDto.class);
    }
}

package com.github.linthias.controllers;

import com.github.linthias.dto.OrderInputDto;
import com.github.linthias.dto.OrderOutputDto;
import com.github.linthias.model.Order;
import com.github.linthias.services.OrderService;
import com.github.linthias.util.ExceptionHandler;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet("/orders/*")
public class OrderController extends BaseController<Order, OrderInputDto, OrderOutputDto> {
    @Override
    public void init() {
        gson = new Gson();
        exceptionHandler = ExceptionHandler.getInstance();
        service = (OrderService) getServletContext().getAttribute("orderService");
    }

    @Override
    protected OrderInputDto parseBody(HttpServletRequest request) throws IOException {
        return gson.fromJson(
                request.getReader().lines().collect(Collectors.joining("\n")),
                OrderInputDto.class);
    }
}

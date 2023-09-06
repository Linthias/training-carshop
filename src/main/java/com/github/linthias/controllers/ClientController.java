package com.github.linthias.controllers;

import com.github.linthias.dto.ClientDto;
import com.github.linthias.model.Client;
import com.github.linthias.services.ClientService;
import com.github.linthias.util.ExceptionHandler;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet("/clients/*")
public class ClientController extends BaseController<Client, ClientDto, ClientDto> {
    @Override
    public void init() {
        gson = new Gson();
        exceptionHandler = ExceptionHandler.getInstance();
        service = (ClientService) getServletContext().getAttribute("clientService");
    }

    @Override
    protected ClientDto parseBody(HttpServletRequest request) throws IOException {
        return gson.fromJson(
                request.getReader().lines().collect(Collectors.joining("\n")),
                ClientDto.class);
    }
}

package com.github.linthias.controllers;

import com.github.linthias.dto.ClientDto;
import com.github.linthias.services.ClientService;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/clients")
public class ClientController extends HttpServlet {
    private ClientService clientService;
    private Gson gson;

    @Override
    public void init() {
        gson = new Gson();
        clientService = (ClientService) getServletContext().getAttribute("clientService");
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
            String body = request.getReader().lines().collect(Collectors.joining("\n"));

            ClientDto newClient = gson.fromJson(body, ClientDto.class);

            //DriverManager.registerDriver(dbDriver);

            clientService.add(newClient);

            /*if (clientRepository.create(newClient)) {
                response.setStatus(201);
            } else {
                response.sendError(500);
            }

             */
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        ClientDto client;
        List<ClientDto> clients;
        String output;

        try {
            long id = getIdParam(request);

            if (id == -1) {
                clients = clientService.getAll();
                if (clients == null) {
                    response.sendError(404);
                    throw new RuntimeException("not found");
                }

                output = gson.toJson(clients);
            } else {
                client = clientService.getById(id);
                if (client == null) {
                    response.sendError(404);
                    throw new RuntimeException("not found");
                }

                output = gson.toJson(client);
            }

            response.setStatus(200);
            PrintWriter writer = response.getWriter();
            writer.println(output);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {
        try {
            String body = request.getReader().lines().collect(Collectors.joining("\n"));

            ClientDto newClient = gson.fromJson(body, ClientDto.class);

            clientService.update(newClient);

            /*if () {
                response.setStatus(200);
            } else {
                response.sendError(500);
            }*/
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        try {
            long id = getIdParam(request);
            if (id == -1) {
                throw new RuntimeException("id not found in parameter list");
            }

            if (clientService.deleteById(id)) {
                response.setStatus(200);
            } else {
                response.sendError(500);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

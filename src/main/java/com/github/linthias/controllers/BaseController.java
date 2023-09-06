package com.github.linthias.controllers;

import com.github.linthias.dto.ErrorDto;
import com.github.linthias.exceptions.BadRequestException;
import com.github.linthias.services.BaseService;
import com.github.linthias.util.ExceptionHandler;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public abstract class BaseController<E, I, O> extends HttpServlet {
    protected BaseService<E, I, O> service;
    protected Gson gson;
    protected ExceptionHandler exceptionHandler;

    protected Long getIdParam(HttpServletRequest request) {
        Long id = null;
        String info = request.getPathInfo();

        if (info != null) {
            String[] chunks = info.split("/");
            id = Long.parseLong(chunks[chunks.length - 1]);
        }

        return id;
    }

    protected abstract I parseBody(HttpServletRequest request) throws IOException;

    protected void initSuccessResponse(HttpServletResponse response,
                                     List<O> dtos,
                                     int httpCode) throws IOException {
        response.setStatus(httpCode);
        response.getWriter().println(gson.toJson(dtos));
    }

    protected void initErrorResponse(HttpServletResponse response, Exception e) throws IOException {
        System.out.println(e.getMessage());
        ErrorDto errorDto = exceptionHandler.handleException(e);
        response.setStatus(errorDto.getHttpCode());
        response.getWriter().println(gson.toJson(errorDto));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            I input = parseBody(request);

            O output = service.add(input);

            initSuccessResponse(response, List.of(output), 201);
        } catch (Exception e) {
            initErrorResponse(response, e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<O> output;

        try {
            Long id = getIdParam(request);

            if (id == null) {
                output = service.getAll();
            } else {
                output = service.getById(id);
            }

            initSuccessResponse(response, output, 200);
        } catch (Exception e) {
            initErrorResponse(response, e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            I input = parseBody(request);

            O output = service.update(input);

            initSuccessResponse(response, List.of(output), 200);
        } catch (Exception e) {
            initErrorResponse(response, e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Long id = getIdParam(request);
            if (id == null) {
                throw new BadRequestException("id was not found in request");
            }

            service.deleteById(id);

            initSuccessResponse(response, List.of(), 200);
        } catch (Exception e) {
            initErrorResponse(response, e);
        }
    }
}

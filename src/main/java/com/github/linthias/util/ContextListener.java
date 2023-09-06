package com.github.linthias.util;

import com.github.linthias.dtoMappers.CarDtoMapper;
import com.github.linthias.dtoMappers.ClientMapper;
import com.github.linthias.dtoMappers.ManufacturerMapper;
import com.github.linthias.dtoMappers.OrderDtoMapper;
import com.github.linthias.repositories.CarRepository;
import com.github.linthias.repositories.ClientRepository;
import com.github.linthias.repositories.ManufacturerRepository;
import com.github.linthias.repositories.OrderRepository;
import com.github.linthias.services.CarService;
import com.github.linthias.services.ClientService;
import com.github.linthias.services.ManufacturerService;
import com.github.linthias.services.OrderService;
import com.github.linthias.validators.CarDtoValidator;
import com.github.linthias.validators.ClientDtoValidator;
import com.github.linthias.validators.ManufacturerDtoValidator;
import com.github.linthias.validators.OrderInputDtoValidator;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

@WebListener
public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Context context;
        DataSource dataSource;
        try {
            context = new InitialContext();
            dataSource = (DataSource) context.lookup("java:comp/env/jdbc/postgres");
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }

        DbConnector connector = new DbConnector(dataSource);
        ServletContext servletContext = sce.getServletContext();

        ClientService clientService = new ClientService(
                ClientRepository.getInstance(connector),
                new ClientDtoValidator(),
                new ClientMapper());
        servletContext.setAttribute("clientService", clientService);
        servletContext.setAttribute("clientRepository", ClientRepository.getInstance(connector));

        ManufacturerService manufacturerService = new ManufacturerService(
                ManufacturerRepository.getInstance(connector),
                new ManufacturerDtoValidator(),
                new ManufacturerMapper());
        servletContext.setAttribute("manufacturerService", manufacturerService);
        servletContext.setAttribute("manufacturerRepository", ManufacturerRepository.getInstance(connector));

        CarService carService = new CarService(
                CarRepository.getInstance(connector),
                ManufacturerRepository.getInstance(connector),
                new CarDtoValidator(),
                new CarDtoMapper());
        servletContext.setAttribute("carService", carService);
        servletContext.setAttribute("carRepository", CarRepository.getInstance(connector));

        OrderService orderService = new OrderService(
                OrderRepository.getInstance(connector),
                ClientRepository.getInstance(connector),
                ManufacturerRepository.getInstance(connector),
                CarRepository.getInstance(connector),
                new OrderInputDtoValidator(),
                new OrderDtoMapper(),
                new ClientMapper(),
                new CarDtoMapper());
        servletContext.setAttribute("orderService", orderService);
        servletContext.setAttribute("orderRepository", OrderRepository.getInstance(connector));
    }
}

package com.github.linthias.util;

import com.github.linthias.dtoMappers.ClientMapper;
import com.github.linthias.repositories.ClientRepository;
import com.github.linthias.services.ClientService;
import com.github.linthias.validators.ClientDtoValidator;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

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

        ClientRepository clientRepository = new ClientRepository(connector);
        ClientService clientService = new ClientService(clientRepository, new ClientDtoValidator(), new ClientMapper());

        servletContext.setAttribute("clientService", clientService);
    }
}

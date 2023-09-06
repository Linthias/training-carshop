package com.github.linthias.dto;

import java.time.LocalDate;

public class OrderOutputDto {
    private Long id;
    private ClientDto client;
    private CarDto car;
    private LocalDate orderDate;

    public OrderOutputDto(Long id, ClientDto client, CarDto car, LocalDate orderDate) {
        this.id = id;
        this.client = client;
        this.car = car;
        this.orderDate = orderDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClientDto getClient() {
        return client;
    }

    public CarDto getCar() {
        return car;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }
}

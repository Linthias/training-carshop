package com.github.linthias.dto;

import java.time.LocalDate;

public class OrderInputDto {
    private Long id;
    private Long clientId;
    private Long carId;
    private LocalDate orderDate;

    public OrderInputDto(Long id, Long clientId, Long carId, LocalDate orderDate) {
        this.id = id;
        this.clientId = clientId;
        this.carId = carId;
        this.orderDate = orderDate;
    }

    public Long getId() {
        return id;
    }

    public Long getClientId() {
        return clientId;
    }

    public Long getCarId() {
        return carId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }
}

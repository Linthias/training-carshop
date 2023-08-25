package com.github.linthias.orders;

import java.time.LocalDate;

public class OrderModel {
    private Long id;
    private Long clientId;
    private Long carId;
    private LocalDate orderDate;

    public OrderModel(Long id, Long clientId, Long carId, LocalDate orderDate) {
        this.id = id;
        this.clientId = clientId;
        this.carId = carId;
        this.orderDate = orderDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }
}

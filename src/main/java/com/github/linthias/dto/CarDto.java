package com.github.linthias.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CarDto {
    private Long id;
    private String manufacturer;
    private String model;
    private String color;
    private Double engineDisplacement;
    private LocalDate manufactureDate;
    private BigDecimal price;

    public CarDto(Long id,
                  String manufacturer,
                  String model,
                  String color,
                  Double engineDisplacement,
                  LocalDate manufactureDate,
                  BigDecimal price) {
        this.id = id;
        this.manufacturer = manufacturer;
        this.model = model;
        this.color = color;
        this.engineDisplacement = engineDisplacement;
        this.manufactureDate = manufactureDate;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getModel() {
        return model;
    }

    public String getColor() {
        return color;
    }

    public Double getEngineDisplacement() {
        return engineDisplacement;
    }

    public LocalDate getManufactureDate() {
        return manufactureDate;
    }

    public BigDecimal getPrice() {
        return price;
    }
}

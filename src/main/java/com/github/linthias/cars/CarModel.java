package com.github.linthias.cars;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CarModel {
    private Long id;
    private String manufacturer;
    private String model;
    private String color;
    private Double engineDisplacement;
    private LocalDate manufactureDate;
    private BigDecimal price;

    public CarModel(Long id, String manufacturer, String model, String color, Double engineDisplacement,
                    LocalDate manufactureDate, BigDecimal price) {
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

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Double getEngineDisplacement() {
        return engineDisplacement;
    }

    public void setEngineDisplacement(Double engineDisplacement) {
        this.engineDisplacement = engineDisplacement;
    }

    public LocalDate getManufactureDate() {
        return manufactureDate;
    }

    public void setManufactureDate(LocalDate manufactureDate) {
        this.manufactureDate = manufactureDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}

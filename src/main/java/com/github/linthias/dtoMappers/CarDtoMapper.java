package com.github.linthias.dtoMappers;

import com.github.linthias.dto.CarDto;
import com.github.linthias.model.Car;
import com.github.linthias.model.Manufacturer;

public class CarDtoMapper implements BaseMapper<Car, CarDto, CarDto> {
    @Override
    public CarDto toDto(Car entity) {
        return null;
    }

    @Override
    public Car toEntity(CarDto dto) {
        return null;
    }

    public CarDto customToDto(Car entity, Manufacturer manufacturer) {
        return new CarDto(
                entity.getId(),
                manufacturer.getName(),
                entity.getModel(),
                entity.getColor(),
                entity.getEngineDisplacement(),
                entity.getManufactureDate(),
                entity.getPrice());
    }

    public Car customToEntity(CarDto dto, Manufacturer manufacturer) {
        return new Car(
                dto.getId(),
                manufacturer.getId(),
                dto.getModel(),
                dto.getColor(),
                dto.getEngineDisplacement(),
                dto.getManufactureDate(), dto.getPrice());
    }
}

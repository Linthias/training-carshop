package com.github.linthias.services;

import com.github.linthias.dto.CarDto;
import com.github.linthias.dtoMappers.BaseMapper;
import com.github.linthias.dtoMappers.CarDtoMapper;
import com.github.linthias.model.Car;
import com.github.linthias.model.Manufacturer;
import com.github.linthias.repositories.BaseRepository;
import com.github.linthias.repositories.ManufacturerRepository;
import com.github.linthias.validators.BaseValidator;

import java.util.ArrayList;
import java.util.List;

public class CarService extends BaseService<Car, CarDto, CarDto> {
    private final BaseRepository<Manufacturer> manufacturerRepository;

    public CarService(BaseRepository<Car> entityRepository,
                      BaseRepository<Manufacturer> manufacturerRepository,
                      BaseValidator<CarDto> dtoValidator,
                      BaseMapper<Car, CarDto, CarDto> dtoMapper) {
        super(entityRepository, dtoValidator, dtoMapper);
        this.manufacturerRepository = manufacturerRepository;
    }

    @Override
    public CarDto add(CarDto input) throws Exception {
        validateInput(input);

        Manufacturer manufacturer = ((ManufacturerRepository) manufacturerRepository)
                .findByName(input.getManufacturer());

        Car output = mainEntityRepository.create(((CarDtoMapper) dtoMapper).customToEntity(input, manufacturer));

        return ((CarDtoMapper) dtoMapper).customToDto(output, manufacturer);
    }

    @Override
    public List<CarDto> getById(Long id) throws Exception {
        Car output = mainEntityRepository.findById(id);
        Manufacturer manufacturer = manufacturerRepository.findById(output.getManufacturerId());

        return List.of(((CarDtoMapper) dtoMapper).customToDto(output, manufacturer));
    }

    @Override
    public List<CarDto> getAll() throws Exception {
        List<Car> cars = mainEntityRepository.findAll();
        List<CarDto> output = new ArrayList<>();

        for (Car car : cars) {
            Manufacturer manufacturer = manufacturerRepository.findById(car.getManufacturerId());
            output.add(((CarDtoMapper) dtoMapper).customToDto(car, manufacturer));
        }

        return output;
    }

    @Override
    public CarDto update(CarDto input) throws Exception {
        validateInput(input);

        Manufacturer manufacturer = ((ManufacturerRepository) manufacturerRepository)
                .findByName(input.getManufacturer());

        Car output = mainEntityRepository.update(((CarDtoMapper) dtoMapper).customToEntity(input, manufacturer));
        manufacturer = manufacturerRepository.findById(output.getManufacturerId());

        return ((CarDtoMapper) dtoMapper).customToDto(output, manufacturer);
    }
}

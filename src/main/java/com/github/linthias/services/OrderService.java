package com.github.linthias.services;

import com.github.linthias.dto.CarDto;
import com.github.linthias.dto.ClientDto;
import com.github.linthias.dto.OrderInputDto;
import com.github.linthias.dto.OrderOutputDto;
import com.github.linthias.dtoMappers.BaseMapper;
import com.github.linthias.dtoMappers.CarDtoMapper;
import com.github.linthias.dtoMappers.OrderDtoMapper;
import com.github.linthias.model.Car;
import com.github.linthias.model.Client;
import com.github.linthias.model.Manufacturer;
import com.github.linthias.model.Order;
import com.github.linthias.repositories.BaseRepository;
import com.github.linthias.validators.BaseValidator;

import java.util.ArrayList;
import java.util.List;

public class OrderService extends BaseService<Order, OrderInputDto, OrderOutputDto> {
    private final BaseRepository<Client> clientRepository;
    private final BaseRepository<Manufacturer> manufacturerRepository;
    private final BaseRepository<Car> carRepository;
    private final BaseMapper<Client, ClientDto, ClientDto> clientMapper;
    private final BaseMapper<Car, CarDto, CarDto> carMapper;

    public OrderService(BaseRepository<Order> entityRepository,
                        BaseRepository<Client> clientRepository,
                        BaseRepository<Manufacturer> manufacturerRepository,
                        BaseRepository<Car> carRepository,
                        BaseValidator<OrderInputDto> dtoValidator,
                        BaseMapper<Order, OrderInputDto, OrderOutputDto> dtoMapper,
                        BaseMapper<Client, ClientDto, ClientDto> clientMapper,
                        BaseMapper<Car, CarDto, CarDto> carMapper) {
        super(entityRepository, dtoValidator, dtoMapper);
        this.clientRepository = clientRepository;
        this.manufacturerRepository = manufacturerRepository;
        this.carRepository = carRepository;
        this.clientMapper = clientMapper;
        this.carMapper = carMapper;
    }

    private CarDto constructCarDto(Order input) throws Exception {
        Car car = carRepository.findById(input.getCarId());
        Manufacturer manufacturer = manufacturerRepository.findById(car.getManufacturerId());

        return ((CarDtoMapper) carMapper).customToDto(car, manufacturer);
    }

    @Override
    public OrderOutputDto add(OrderInputDto input) throws Exception {
        validateInput(input);

        ClientDto clientDto = clientMapper.toDto(clientRepository.findById(input.getClientId()));

        return ((OrderDtoMapper) dtoMapper)
                .customToDto(mainEntityRepository
                        .create(dtoMapper.toEntity(input)), clientDto, constructCarDto(dtoMapper.toEntity(input)));
    }

    @Override
    public List<OrderOutputDto> getById(Long id) throws Exception {
        Order order = mainEntityRepository.findById(id);

        ClientDto clientDto = clientMapper.toDto(clientRepository.findById(order.getClientId()));

        return List.of(((OrderDtoMapper) dtoMapper).customToDto(order, clientDto, constructCarDto(order)));
    }

    @Override
    public List<OrderOutputDto> getAll() throws Exception {
        List<Order> orders = mainEntityRepository.findAll();
        List<OrderOutputDto> output = new ArrayList<>();

        for (Order order : orders) {
            ClientDto clientDto = clientMapper.toDto(clientRepository.findById(order.getClientId()));

            output.add(((OrderDtoMapper) dtoMapper).customToDto(order, clientDto, constructCarDto(order)));
        }

        return output;
    }

    @Override
    public OrderOutputDto update(OrderInputDto input) throws Exception {
        validateInput(input);

        ClientDto clientDto = clientMapper.toDto(clientRepository.findById(input.getClientId()));

        return ((OrderDtoMapper) dtoMapper)
                .customToDto(mainEntityRepository
                        .update(dtoMapper.toEntity(input)), clientDto, constructCarDto(dtoMapper.toEntity(input)));
    }
}

package com.github.linthias.dtoMappers;

import com.github.linthias.dto.CarDto;
import com.github.linthias.dto.ClientDto;
import com.github.linthias.dto.OrderInputDto;
import com.github.linthias.dto.OrderOutputDto;
import com.github.linthias.model.Order;

public class OrderDtoMapper implements BaseMapper<Order, OrderInputDto, OrderOutputDto> {
    @Override
    public OrderOutputDto toDto(Order entity) {
        return null;
    }

    @Override
    public Order toEntity(OrderInputDto dto) {
        return new Order(
                dto.getId(),
                dto.getClientId(),
                dto.getCarId(),
                dto.getOrderDate());
    }

    public OrderOutputDto customToDto(Order entity, ClientDto client, CarDto car) {
        return new OrderOutputDto(
                entity.getId(),
                client,
                car,
                entity.getOrderDate());
    }
}

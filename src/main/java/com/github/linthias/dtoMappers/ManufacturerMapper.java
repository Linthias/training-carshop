package com.github.linthias.dtoMappers;

import com.github.linthias.dto.ManufacturerDto;
import com.github.linthias.model.Manufacturer;

public class ManufacturerMapper implements BaseMapper<Manufacturer, ManufacturerDto, ManufacturerDto> {
    @Override
    public ManufacturerDto toDto(Manufacturer entity) {
        return new ManufacturerDto(entity.getId(), entity.getName());
    }

    @Override
    public Manufacturer toEntity(ManufacturerDto dto) {
        return new Manufacturer(dto.getId(), dto.getName());
    }
}

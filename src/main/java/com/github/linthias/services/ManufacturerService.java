package com.github.linthias.services;

import com.github.linthias.dto.ManufacturerDto;
import com.github.linthias.dtoMappers.BaseMapper;
import com.github.linthias.model.Manufacturer;
import com.github.linthias.repositories.BaseRepository;
import com.github.linthias.validators.BaseValidator;

public class ManufacturerService extends BaseService<Manufacturer, ManufacturerDto, ManufacturerDto> {
    public ManufacturerService(BaseRepository<Manufacturer> entityRepository,
                               BaseValidator<ManufacturerDto> dtoValidator,
                               BaseMapper<Manufacturer, ManufacturerDto, ManufacturerDto> dtoMapper) {
        super(entityRepository, dtoValidator, dtoMapper);
    }
}

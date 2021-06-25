package com.guru.msscbeerservice.web.mappers;

import com.guru.msscbeerservice.web.domain.Beer;
import com.guru.msscbeerservice.web.model.BeerDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface BeerMapper {

    BeerDto beerToBeerDto(Beer beer);

    Beer beerDtoToBeer(BeerDto beerDto);

}

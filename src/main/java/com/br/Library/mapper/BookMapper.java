package com.br.Library.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.br.Library.dto.BookRequestDTO;
import com.br.Library.model.BookModel;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "availableCopies", ignore = true)
    BookModel toEntity(BookRequestDTO bookRequestDTO);
}

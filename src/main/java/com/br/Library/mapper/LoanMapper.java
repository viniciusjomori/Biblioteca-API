package com.br.Library.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.br.Library.dto.BookResponseDTO;
import com.br.Library.dto.LoanResponseDTO;
import com.br.Library.dto.UserResponseDTO;
import com.br.Library.model.BookModel;
import com.br.Library.model.LoanModel;
import com.br.Library.model.UserModel;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LoanMapper {
    LoanMapper INSTANCE = Mappers.getMapper(LoanMapper.class);

    @Mapping(target = "book", source = "book", qualifiedByName = "bookModelToDTO")
    @Mapping(target = "client", source = "client", qualifiedByName = "userModelToDTO")
    LoanResponseDTO toResponseDTO(LoanModel model);

    Iterable<LoanResponseDTO> toListResponseDTO(Iterable<LoanModel> loans);

    @Named("userModelToDTO")
    default UserResponseDTO userModelsToDTOs(UserModel model) {
        return Mappers.getMapper(UserMapper.class).toResponseDTO(model);
    }

    @Named("bookModelToDTO")
    default BookResponseDTO bookModelToDTO(BookModel model) {
        return Mappers.getMapper(BookMapper.class).toResponseDTO(model);
    }
}

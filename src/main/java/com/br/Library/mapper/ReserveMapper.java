package com.br.Library.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.br.Library.dto.BookInfo;
import com.br.Library.dto.ReserveInfo;
import com.br.Library.dto.ReserveResponseDTO;
import com.br.Library.dto.UserInfo;
import com.br.Library.model.BookModel;
import com.br.Library.model.ReserveModel;
import com.br.Library.model.UserModel;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReserveMapper {
    ReserveMapper INSTANCE = Mappers.getMapper(ReserveMapper.class);

    @Mapping(target = "book", source = "book", qualifiedByName = "bookModelToDTO")
    @Mapping(target = "client", source = "client", qualifiedByName = "userModelToDTO")
    ReserveResponseDTO toResponseDTO(ReserveModel model);

    Iterable<ReserveResponseDTO> toListResponseDTO(Iterable<ReserveModel> Reserves);

    ReserveInfo toInfo(ReserveModel model);
    Iterable<ReserveInfo> toListInfo(Iterable<ReserveModel> model);

    @Named("userModelToDTO")
    default UserInfo userModelsToDTOs(UserModel model) {
        return Mappers.getMapper(UserMapper.class).toInfo(model);
    }

    @Named("bookModelToDTO")
    default BookInfo bookModelToDTO(BookModel model) {
        return Mappers.getMapper(BookMapper.class).toInfo(model);
    }
}

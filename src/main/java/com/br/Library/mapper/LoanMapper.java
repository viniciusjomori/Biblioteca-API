package com.br.Library.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.br.Library.dto.BookInfo;
import com.br.Library.dto.LoanInfo;
import com.br.Library.dto.LoanResponseDTO;
import com.br.Library.dto.UserInfo;
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

    LoanInfo toInfo(LoanModel model);
    Iterable<LoanInfo> toListInfo(Iterable<LoanModel> model);

    @Named("userModelToDTO")
    default UserInfo userModelsToDTOs(UserModel model) {
        return Mappers.getMapper(UserMapper.class).toInfo(model);
    }

    @Named("bookModelToDTO")
    default BookInfo bookModelToDTO(BookModel model) {
        return Mappers.getMapper(BookMapper.class).toInfo(model);
    }
}

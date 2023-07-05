package com.br.Library.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.br.Library.dto.ClientResponseDTO;
import com.br.Library.dto.LoanInfo;
import com.br.Library.dto.ReserveInfo;
import com.br.Library.model.LoanModel;
import com.br.Library.model.ReserveModel;
import com.br.Library.model.UserModel;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClientMapper {
    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    @Mapping(target = "loans", source = "loans", qualifiedByName = "loansModelToInfo")
    @Mapping(target = "reserves", source = "reserves", qualifiedByName = "reservesModelToInfo")
    ClientResponseDTO toResponseDTO(UserModel client, Iterable<LoanModel> loans, Iterable<ReserveModel> reserves);

    @Mapping(target = "loans", ignore = true)
    @Mapping(target = "reserves", ignore = true)
    ClientResponseDTO toResponseDTO(UserModel client);

    @Named("loansModelToInfo")
    default Iterable<LoanInfo> loansModelToInfo(Iterable<LoanModel> loans) {
        return Mappers.getMapper(LoanMapper.class).toListInfo(loans);
    }

    @Named("reservesModelToInfo")
    default Iterable<ReserveInfo> reservesModelToInfo(Iterable<ReserveModel> reserves) {
        return Mappers.getMapper(ReserveMapper.class).toListInfo(reserves);
    }
}

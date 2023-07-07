package com.br.Library.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.br.Library.dto.RoleResponseDTO;
import com.br.Library.dto.UserInfo;
import com.br.Library.model.RoleModel;
import com.br.Library.model.UserModel;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    Iterable<RoleResponseDTO> toListResponseDTO(Iterable<RoleModel> users);

    @Mapping(target = "users", source = "users", qualifiedByName = "modelsToDTOs")
    RoleResponseDTO toResponseDTO(RoleModel roleModel);

    @Named("modelsToDTOs")
    default Iterable<UserInfo> modelsToDTOs(Iterable<UserModel> userModels) {
        return Mappers.getMapper(UserMapper.class).toListInfo(userModels);
    }

}

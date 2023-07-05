package com.br.Library.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.br.Library.dto.UserResponseDTO;
import com.br.Library.enums.RoleName;
import com.br.Library.model.RoleModel;
import com.br.Library.model.UserModel;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    Iterable<UserResponseDTO> toListResponseDTO(Iterable<UserModel> users);

    @Mapping(source = "role", target = "role", qualifiedByName = "roleModelToName")
    UserResponseDTO toResponseDTO(UserModel model);

    @Named("roleModelToName")
    default RoleName roleModelToName(RoleModel model) {
        return model.getName();
    }
}

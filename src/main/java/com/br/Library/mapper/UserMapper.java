package com.br.Library.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.br.Library.dto.UserInfo;
import com.br.Library.enums.RoleName;
import com.br.Library.model.RoleModel;
import com.br.Library.model.UserModel;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    Iterable<UserInfo> toListResponseDTO(Iterable<UserModel> users);

    @Mapping(source = "role", target = "role", qualifiedByName = "roleModelToName")
    UserInfo toInfo(UserModel model);

    @Named("roleModelToName")
    default RoleName roleModelToName(RoleModel model) {
        return model.getName();
    }
}

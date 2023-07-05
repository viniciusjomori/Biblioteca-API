package com.br.Library.mapper;

import java.util.ArrayList;
import java.util.Collection;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.br.Library.dto.RoleResponseDTO;
import com.br.Library.dto.UserResponseDTO;
import com.br.Library.model.RoleModel;
import com.br.Library.model.UserModel;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    Iterable<RoleResponseDTO> toListResponseDTO(Iterable<RoleModel> users);

    @Mapping(target = "users", source = "users", qualifiedByName = "modelsToDTOs")
    RoleResponseDTO toResponseDTO(RoleModel roleModel);

    @Named("modelsToDTOs")
    default Collection<UserResponseDTO> modelsToDTOs(Collection<UserModel> userModels) {
        Iterable<UserResponseDTO> iterable = Mappers.getMapper(UserMapper.class).toListResponseDTO(userModels);
        Collection<UserResponseDTO> collection = new ArrayList<>();
        iterable.forEach(collection::add);
        return collection;
    }

}

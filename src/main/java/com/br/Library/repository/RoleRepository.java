package com.br.Library.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.Library.enums.RoleName;
import com.br.Library.model.RoleModel;

public interface RoleRepository extends JpaRepository<RoleModel, Long> {
    Optional<RoleModel> findByName(RoleName name);
    boolean existsByName(RoleName name);
    Iterable<RoleModel> findByNameIn(Iterable<RoleName> names);
}

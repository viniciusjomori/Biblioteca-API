package com.br.Library.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.br.Library.dto.EmployeeRequestDTO;
import com.br.Library.dto.UserRequestDTO;
import com.br.Library.enums.RoleName;
import com.br.Library.model.RoleModel;
import com.br.Library.repository.RoleRepository;
import com.br.Library.service.ClientService;
import com.br.Library.service.EmployeeService;

import jakarta.transaction.Transactional;

@Component
public class SetupRoles implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ClientService clientService;

    @Autowired
    private EmployeeService employeeService;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(alreadySetup) return;
        
        createRoleIfNotFound(RoleName.ROLE_CLIENT);

        createRoleIfNotFound(RoleName.ROLE_EMPLOYEE);

        createRoleIfNotFound(RoleName.ROLE_ADMINISTRATOR);

        /* clientService.createClient(new UserRequestDTO("client", "senha"));
        employeeService.createEmployee(new EmployeeRequestDTO("employee", "senha", "ROLE_EMPLOYEE"));
        employeeService.createEmployee(new EmployeeRequestDTO("adm", "senha", "ROLE_ADMINISTRATOR")); */

        alreadySetup = true;
    }

    @Transactional
    public void createRoleIfNotFound(RoleName roleName) {
        if(roleRepository.existsByName(roleName)) return;

        RoleModel roleModel = new RoleModel();
        roleModel.setName(roleName);

        roleRepository.save(roleModel);
    }

}

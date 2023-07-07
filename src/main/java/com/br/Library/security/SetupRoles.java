package com.br.Library.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.br.Library.enums.RoleName;
import com.br.Library.model.RoleModel;
import com.br.Library.model.UserModel;
import com.br.Library.repository.RoleRepository;
import com.br.Library.repository.UserRepository;

import jakarta.transaction.Transactional;

@Component
public class SetupRoles implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(alreadySetup) return;
        
        RoleModel clientRole = createRoleIfNotFound(RoleName.ROLE_CLIENT);

        RoleModel employeeRole = createRoleIfNotFound(RoleName.ROLE_EMPLOYEE);

        RoleModel admRole = createRoleIfNotFound(RoleName.ROLE_ADMINISTRATOR);

        createUserIfNotFound(1, "client", "senha", clientRole);
        createUserIfNotFound(2, "employee", "senha", employeeRole);
        createUserIfNotFound(3, "adm", "senha", admRole);

        
        alreadySetup = true;
    }

    @Transactional
    public RoleModel createRoleIfNotFound(RoleName roleName) {
        
        Optional<RoleModel> optional = roleRepository.findByName(roleName);

        if(optional.isPresent()) {
            return optional.get();
        }

        RoleModel role = new RoleModel();
        role.setName(roleName);
        return roleRepository.save(role);
    }

    @Transactional
    public void createUserIfNotFound(long id, String username, String password, RoleModel role) {
        if(!userRepository.existsById(id)) {
            UserModel user = new UserModel();
            user.setId(id);
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            user.setRole(role);
            userRepository.save(user);
        }
    }

}

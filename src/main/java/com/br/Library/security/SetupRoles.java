package com.br.Library.security;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.br.Library.enums.RoleName;
import com.br.Library.model.BookModel;
import com.br.Library.model.RoleModel;
import com.br.Library.model.UserModel;
import com.br.Library.repository.BookRepository;
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
    private BookRepository bookRepository;

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

        createBookIfNotFound(1, "Harry Potter", "JK Rolling", "Rocco", LocalDate.of(1996, 6, 27), 10);
        createBookIfNotFound(2, "Sapiens: Uma Breve Hist\u00F3ria da Humanidade", "Yuval Noah Harari", "L&PM Editores", LocalDate.of(2015, 4, 10), 10);
        createBookIfNotFound(3, "Naruto", "Masashi Kishimoto", "Shueisha", LocalDate.of(1999, 9, 21), 10);
        createBookIfNotFound(4, "Romeu e Julieta", "William Shakespeare", "Oxford University Press", LocalDate.of(2018, 1, 1), 10);
        createBookIfNotFound(5, "Dom Casmurro", "Machado de Assis", "Editora Nova Aguilar", LocalDate.of(1975, 8, 14), 10);

        
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

    @Transactional
    public void createBookIfNotFound(long id, String title, String author, String publishingCompany, LocalDate releaseDate, int totalCopies) {
        if(!bookRepository.existsById(id)){
            BookModel book = new BookModel();
            book.setId(id);
            book.setTitle(title);
            book.setAuthor(author);
            book.setPublishingCompany(publishingCompany);
            book.setReleaseDate(releaseDate);
            book.setTotalCopies(totalCopies);
            book.setAvailableCopies(totalCopies);
            bookRepository.save(book);
        }
    }

}

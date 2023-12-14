package org.openskyt.skytale.service;

import jakarta.transaction.Transactional;
import org.openskyt.skytale.dto.RegistrationRequestDto;
import org.openskyt.skytale.models.User;
import org.openskyt.skytale.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    // -----------------------------------------------------------------------------------------------------------------

    public void createUser(RegistrationRequestDto dto) {
        User newUser = new User(
                dto.username(),
                passwordEncoder.encode(dto.password())
        );
        userRepo.save(newUser);
    }

    // TODO errhandl
    // fixme
    public void deleteUser(Long userId) {
        userRepo.delete(
                userRepo.getReferenceById(userId)
        );
    }

    // TODO create dto
    public User getById(Long userId) {
        return userRepo.getReferenceById(userId);
    }


    public User getByUsername(String username) {
        return userRepo.findByName(username).orElse(null);
    }

    public Optional<User> getOptionalByUsername(String username) {
        return userRepo.findByName(username);
    }

    public List<User> getAll() {
        return userRepo.findAll();
    }
}

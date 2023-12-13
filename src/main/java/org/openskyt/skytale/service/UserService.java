package org.openskyt.skytale.service;

import org.openskyt.skytale.dto.RegistrationRequest;
import org.openskyt.skytale.models.User;
import org.openskyt.skytale.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    // -----------------------------------------------------------------------------------------------------------------

    public void createUser(RegistrationRequest dto) {
        User newUser = new User(
                dto.getUsername(),
                passwordEncoder.encode(dto.getPassword())
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

    public List<User> getAll() {
        return userRepo.findAll();
    }
}

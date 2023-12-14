package org.openskyt.skytale.service;

import jakarta.transaction.Transactional;
import org.openskyt.skytale.models.User;
import org.openskyt.skytale.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ContactService {
    private UserRepository userRepository;
    private UserService userService;

    public  ContactService (UserRepository userRepository, UserService userService){
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Transactional
    public void addContact(Long actualUserId, Long newContactId){
        User actualUser = userService.getUserById(actualUserId);
        User newContact = userService.getUserById(newContactId);
        actualUser.getUserContacts().add(newContact);
        userRepository.save(actualUser);
    }
    @Transactional
    public void deleteContact(Long actualUserId, Long newContactId){
        User actualUser = userService.getUserById(actualUserId);
        User newContact = userService.getUserById(newContactId);
        actualUser.getUserContacts().remove(newContact);
        userRepository.save(actualUser);
    }



}

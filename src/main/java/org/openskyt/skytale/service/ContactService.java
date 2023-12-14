package org.openskyt.skytale.service;

import jakarta.transaction.Transactional;
import org.openskyt.skytale.dto.ContactUserRetrieveDto;
import org.openskyt.skytale.models.User;
import org.openskyt.skytale.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ContactService {
    private final UserRepository userRepository;
    private final UserService userService;

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
        //TODO - connect to frontend
    }
    @Transactional
    public void deleteContact(Long actualUserId, Long newContactId){
        User actualUser = userService.getUserById(actualUserId);
        User newContact = userService.getUserById(newContactId);
        actualUser.getUserContacts().remove(newContact);
        userRepository.save(actualUser);
        //TODO - connect to frontend
    }

    public List<ContactUserRetrieveDto> getAllContactsOfUser2(Long actualUserId) {
        List<User> contacts = userRepository.findById(actualUserId).get().getUserContacts();
        return contacts.stream().map(ContactUserRetrieveDto::new).collect(Collectors.toList());
        //TODO - connect to frontend
    }




}

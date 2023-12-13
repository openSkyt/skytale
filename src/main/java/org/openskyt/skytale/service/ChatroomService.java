package org.openskyt.skytale.service;

import org.openskyt.skytale.models.Chatroom;
import org.openskyt.skytale.repositories.ChatroomRepository;
import org.openskyt.skytale.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatroomService {

    private ChatroomRepository roomRepo;
    private UserRepository userRepo;

    @Autowired
    public ChatroomService(ChatroomRepository roomRepo, UserRepository userRepo) {
        this.roomRepo = roomRepo;
        this.userRepo = userRepo;
    }

    // -----------------------------------------------------------------------------------------------------------------

    public Chatroom createRoom(Long ownerId, String name) {
        Chatroom room = new Chatroom(name, userRepo.getReferenceById(ownerId));
        roomRepo.save(room);
        return room;
    }
}

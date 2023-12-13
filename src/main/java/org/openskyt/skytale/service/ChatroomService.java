package org.openskyt.skytale.service;

import jakarta.transaction.Transactional;
import org.openskyt.skytale.models.Chatroom;
import org.openskyt.skytale.models.Message;
import org.openskyt.skytale.models.User;
import org.openskyt.skytale.repositories.ChatroomRepository;
import org.openskyt.skytale.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    // TODO - remember to set owner as a participant
    public Chatroom createRoom(Long ownerId, String name) {
        Chatroom room = new Chatroom(name, userRepo.getReferenceById(ownerId));
        roomRepo.save(room);
        return room;
    }

    // TODO - remember to validate owner
    public void deleteRoom(Long id) {
        Chatroom roomReference = roomRepo.getReferenceById(id);
        roomRepo.delete(roomReference);
    }

    @Transactional
    public void addParticipant(Long roomId, Long participantId) {
        Chatroom roomReference = roomRepo.getReferenceById(roomId);
        User userReference = userRepo.getReferenceById(participantId);

        roomReference.getParticipants().add(userReference);
    }

    public List<Chatroom> getAll() {
        return roomRepo.findAll();
    }

    public List<Chatroom> getByParticipantId(Long participantId) {
        return roomRepo.findAllByParticipantsId(participantId);
    }

    public List<User> getAllParticipantsInRoom(Long roomId) {
        return roomRepo.getReferenceById(roomId).getParticipants();
    }

    public List<Message> getAllMessagesInRoom(Long roomId) {
        return roomRepo.getReferenceById(roomId).getMessagesInRoom();
    }

}

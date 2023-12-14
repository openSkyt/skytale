package org.openskyt.skytale.service;

import jakarta.transaction.Transactional;
import org.openskyt.skytale.dto.MessageRequestDto;
import org.openskyt.skytale.models.Message;
import org.openskyt.skytale.repositories.ChatroomRepository;
import org.openskyt.skytale.repositories.MessageRepository;
import org.openskyt.skytale.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private MessageRepository messageRepo;
    private UserRepository userRepo;
    private ChatroomRepository chatroomRepo;

    @Autowired
    public MessageService(MessageRepository messageRepo, UserRepository userRepo, ChatroomRepository chatroomRepo) {
        this.messageRepo = messageRepo;
        this.userRepo = userRepo;
        this.chatroomRepo = chatroomRepo;
    }

    @Transactional
    public void createMessage(MessageRequestDto dto) {
        Message message = new Message(
                userRepo.getReferenceById(dto.authorId()),
                chatroomRepo.getReferenceById(dto.roomId()),
                dto.message()
        );
        messageRepo.save(message);
    }

    public List<Message> getAllByAuthorId(Long userId) {
        return messageRepo.findAllByAuthorId(userId);
    }

    public List<Message> getAllByRoomId(Long roomId) {
        return messageRepo.findAllByChatroomId(roomId);
    }
}

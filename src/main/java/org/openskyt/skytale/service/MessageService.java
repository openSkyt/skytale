package org.openskyt.skytale.service;

import org.openskyt.skytale.repositories.MessageRepository;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private MessageRepository messageRepo;

    
    public MessageService(MessageRepository messageRepo) {
        this.messageRepo = messageRepo;
    }
}

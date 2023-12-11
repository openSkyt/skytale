package org.openskyt.skytale;

import org.openskyt.skytale.models.Chatroom;
import org.openskyt.skytale.models.Message;
import org.openskyt.skytale.models.User;
import org.openskyt.skytale.repositories.ChatroomRepository;
import org.openskyt.skytale.repositories.MessageRepository;
import org.openskyt.skytale.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SkytaleApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SkytaleApplication.class, args);
    }

    @Autowired
    private ChatroomRepository chatroomRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        User user1 = new User("Dan");
        User user2 = new User("Marek");
        Message message1 = new Message("Ahoj");
        Message message2 = new Message("Cau");
        Chatroom chatroom1 = new Chatroom("xChat");

        userRepository.save(user1);
        userRepository.save(user2);
        chatroom1.setUser(user1);
        chatroom1.setUser(user2);
        chatroomRepository.save(chatroom1);

        message1.setUser(user1);
        message2.setUser(user2);
        message1.setChatroom(chatroom1);
        message2.setChatroom(chatroom1);
        messageRepository.save(message1);
        messageRepository.save(message2);










    }

}

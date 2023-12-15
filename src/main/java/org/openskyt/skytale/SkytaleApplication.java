package org.openskyt.skytale;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.openskyt.skytale.models.Chatroom;
import org.openskyt.skytale.models.Message;
import org.openskyt.skytale.models.User;
import org.openskyt.skytale.repositories.ChatroomRepository;
import org.openskyt.skytale.repositories.MessageRepository;
import org.openskyt.skytale.repositories.UserRepository;
import org.openskyt.skytale.service.ChatroomService;
import org.openskyt.skytale.service.ContactService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class SkytaleApplication implements CommandLineRunner {

    private final ChatroomRepository chatroomRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ChatroomService chatroomService;

    public static void main(String[] args) {
        SpringApplication.run(SkytaleApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        User user1 = new User("Dan", passwordEncoder.encode("1234"));
        User user3ownerOfchat = new User("OWNER of chat", passwordEncoder.encode("1234"));
        User user2 = new User("MarekL", passwordEncoder.encode("1234"));
        User us3r = new User("admin", passwordEncoder.encode("admin"));

        Message message1 = new Message("Ahoj");
        Chatroom chatroom1 = new Chatroom("xChat");

        //database testing data
        userRepository.save(us3r);
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3ownerOfchat);
        chatroom1.setOwnerOfChatroom(user3ownerOfchat);
        chatroom1.getParticipants().add(user1);
        chatroom1.getParticipants().add(user2);
        chatroomRepository.save(chatroom1);
        message1.setAuthor(user1);
        message1.setChatroom(chatroom1);
        messageRepository.save(message1);

        //database testing data
        //database testing for adding contacts
//        user1.setUserContacts(List.of(user2, user3ownerOfchat));
//        us3r.setUserContacts(List.of(user1, user2, user3ownerOfchat));
//        userRepository.save(us3r);

        userRepository.save(user1);

        chatroomService.createRoom(1L, "test"); // ID = 2
        Chatroom room2 = chatroomService.createRoom(2L, "test2");
        chatroomService.createRoom(2L, "test3");

        chatroomService.addParticipant(2L, 1L);
        chatroomService.addParticipant(3L, 1L);

        System.out.println(chatroomService.getAll());
        System.out.println(chatroomService.getByParticipantId(1L));

    }
}

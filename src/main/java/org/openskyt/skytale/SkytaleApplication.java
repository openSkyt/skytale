package org.openskyt.skytale;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class SkytaleApplication implements CommandLineRunner {

    private ChatroomRepository chatroomRepository;
    private MessageRepository messageRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private ChatroomService chatroomService;
    private ContactService contactService;

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
        Message message2 = new Message("Cau");
        Message message3 = new Message("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Etiam ligula pede, sagittis quis, interdum ultricies, scelerisque eu. Integer malesuada. Aliquam erat volutpat.");
        Message message4 = new Message("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Etiam ligula pede, sagittis quis, interdum ultricies, scelerisque eu. Integer malesuada. Aliquam erat volutpat.");
        Message message5 = new Message("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Etiam ligula pede, sagittis quis, interdum ultricies, scelerisque eu. Integer malesuada. Aliquam erat volutpat.");
        Message message6 = new Message("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Etiam ligula pede, sagittis quis, interdum ultricies, scelerisque eu. Integer malesuada. Aliquam erat volutpat.");
        Message message7 = new Message("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Etiam ligula pede, sagittis quis, interdum ultricies, scelerisque eu. Integer malesuada. Aliquam erat volutpat.");
        Message message8 = new Message("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Etiam ligula pede, sagittis quis, interdum ultricies, scelerisque eu. Integer malesuada. Aliquam erat volutpat.");
        Message message9 = new Message("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Etiam ligula pede, sagittis quis, interdum ultricies, scelerisque eu. Integer malesuada. Aliquam erat volutpat.");
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
        message2.setAuthor(user2);
        message3.setAuthor(user2);
        message4.setAuthor(user1);
        message5.setAuthor(user1);
        message6.setAuthor(user2);
        message7.setAuthor(user1);
        message8.setAuthor(user2);
        message9.setAuthor(user1);
        message1.setChatroom(chatroom1);
        message2.setChatroom(chatroom1);
        message3.setChatroom(chatroom1);
        message4.setChatroom(chatroom1);
        message5.setChatroom(chatroom1);
        message6.setChatroom(chatroom1);
        message7.setChatroom(chatroom1);
        message8.setChatroom(chatroom1);
        message9.setChatroom(chatroom1);
        messageRepository.save(message1);
        messageRepository.save(message2);

        //database testing data
        //database testing for adding contacts
//        user1.setUserContacts(List.of(user2, user3ownerOfchat));
//        us3r.setUserContacts(List.of(user1, user2, user3ownerOfchat));
//        userRepository.save(us3r);

        userRepository.save(user1);

        //database testing for adding contacts
        messageRepository.save(message3);
        messageRepository.save(message4);
        messageRepository.save(message5);
        messageRepository.save(message6);
        messageRepository.save(message7);
        messageRepository.save(message8);
        messageRepository.save(message9);

        chatroomService.createRoom(1L, "test"); // ID = 2
        Chatroom room2 = chatroomService.createRoom(2L, "test2");
        chatroomService.createRoom(2L, "test3");

        chatroomService.addParticipant(2L, 1L);
        chatroomService.addParticipant(3L, 1L);

        System.out.println(chatroomService.getAll());
        System.out.println(chatroomService.getByParticipantId(1L));


        contactService.addContact(1L,2L);
        contactService.addContact(1L,3L);
        contactService.addContact(1L,4L);
        contactService.deleteContact(1L,2L);

    }
}

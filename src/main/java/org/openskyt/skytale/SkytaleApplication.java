package org.openskyt.skytale;

import lombok.AllArgsConstructor;
import org.openskyt.skytale.models.Chatroom;
import org.openskyt.skytale.models.Message;
import org.openskyt.skytale.models.User;
import org.openskyt.skytale.repositories.ChatroomRepository;
import org.openskyt.skytale.repositories.MessageRepository;
import org.openskyt.skytale.repositories.UserRepository;
import org.openskyt.skytale.service.ChatroomService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@AllArgsConstructor
public class SkytaleApplication implements CommandLineRunner {

    private ChatroomRepository chatroomRepository;
    private MessageRepository messageRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    private ChatroomService chatroomService;

    public static void main(String[] args) {
        SpringApplication.run(SkytaleApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        User user1 = new User("Dan", passwordEncoder.encode("1234"));
        User user3ownerOfchat = new User("OWNER of chat", passwordEncoder.encode("1234"));
        User user2 = new User("MarekL",passwordEncoder.encode("1234"));
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
        message1.setUser(user1);
        message2.setUser(user2);
        message3.setUser(user2);
        message4.setUser(user1);
        message5.setUser(user1);
        message6.setUser(user2);
        message7.setUser(user1);
        message8.setUser(user2);
        message9.setUser(user1);
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
        messageRepository.save(message3);
        messageRepository.save(message4);
        messageRepository.save(message5);
        messageRepository.save(message6);
        messageRepository.save(message7);
        messageRepository.save(message8);
        messageRepository.save(message9);

        chatroomService.createRoom(1L, "test");
    }
}

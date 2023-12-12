package org.openskyt.skytale.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "chatrooms")
public class Chatroom {

    @Id
    @GeneratedValue
    private Long id;
    private String name;



    @OneToMany(mappedBy = "chatroom")
    private List<Message> messagesInRoom = new ArrayList<>();


    @ManyToOne
    private User user;

    public Chatroom(String chatroom) {
        this.name = chatroom;
    }

    @ManyToMany
    @JoinTable(
            name = "UsersInChatroom",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "chatroom_id")
    )

    private List<User> listOfUsersInRoom = new ArrayList<>();

    //TODO - zkontrolovat vazbu ManyToMany mezi userem a chatroomem
    //TODO - konstruktory dodělat?
}

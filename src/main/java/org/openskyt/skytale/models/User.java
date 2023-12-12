package org.openskyt.skytale.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String password;

    // TODO: add timestamp

    @OneToMany(mappedBy = "user") //TODO - Cascade?
    private List<Message> message;

    @OneToMany(mappedBy = "userOwnerOfChatroom")
    private List<Chatroom> chatroomCreatedByUser = new ArrayList<>();

    @ManyToMany(mappedBy = "participants")
    private List<Chatroom> chatrooms = new ArrayList<>();



    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
    //TODO - konstruktory dodělat?
}
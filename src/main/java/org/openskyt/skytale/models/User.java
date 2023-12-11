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
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "user") //TODO - Cascade?
    private List<Message> message;

    private String name;

    @OneToMany(mappedBy = "user")
    private List<Chatroom> chatroomCreatedByUser = new ArrayList<>();

    public User(String name) {
        this.name = name;
    }
    //TODO - konstruktory dodělat?
}
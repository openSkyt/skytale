package org.openskyt.skytale.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
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
    @NaturalId
    private String name;
    private String password;

    @OneToMany(mappedBy = "author")
    private List<Message> message;

    @OneToMany(mappedBy = "ownerOfChatroom")
    private List<Chatroom> chatroomCreatedByUser = new ArrayList<>();

    @ManyToMany(mappedBy = "participants")
    private List<Chatroom> chatrooms = new ArrayList<>();

    @ManyToMany
    List<User> userContacts=new ArrayList<>();

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
}

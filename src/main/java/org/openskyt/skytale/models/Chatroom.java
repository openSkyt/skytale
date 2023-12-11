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



    @OneToMany
    private List<Message> messagesInRoom = new ArrayList<>();


}

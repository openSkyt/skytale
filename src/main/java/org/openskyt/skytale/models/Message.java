package org.openskyt.skytale.models;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue
    private Long id;
    private String text;
    private Timestamp timeStamp;

    @ManyToOne
    private User author;

    @ManyToOne
    private Chatroom chatroom;

    public Message(String message) {
        this.text = message;
        this.timeStamp = new Timestamp(new Date().getTime());
    }

    public Message(User author, Chatroom room, String message) {
        this.author = author;
        this.chatroom = room;
        this.text = message;
    }
}

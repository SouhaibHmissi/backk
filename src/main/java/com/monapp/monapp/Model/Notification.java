package com.monapp.monapp.Model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "sender_id")
    private int sender_id;
    @Column(name = "recipient_id")
    private int recipient_id;
    @Column(name = "subject")
    private String subject;
    @Lob
    @Column(name = "content")
    private String content;
    @Column(name = "sent_at")
    private Timestamp sent_at;

}

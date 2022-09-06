package com.app.vple.domain;


import com.app.vple.domain.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "chatting_messages")
@Builder
@AllArgsConstructor
public class ChattingMessage extends BaseTime {

    public ChattingMessage() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatting_room")
    private ChattingRoom room;

    @Column(nullable = false)
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender")
    private User sender;

    @Enumerated
    private MessageType type;

    public void setMessage(String message) {
        this.message = message;
    }
}

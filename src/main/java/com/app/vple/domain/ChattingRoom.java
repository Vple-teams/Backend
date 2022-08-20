package com.app.vple.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "chatting_room")
@Builder
@AllArgsConstructor
@Getter
public class ChattingRoom {

    public ChattingRoom() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userA")
    private User userA;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userB")
    private User userB;

    @Column(nullable = false)
    private String userANickname;

    @Column(nullable = false)
    private String userBNickname;

    @OneToMany(mappedBy = "room")
    private List<ChattingMessage> messages;

    @Column(nullable = false, name = "session_id")
    private String sessionId;

}

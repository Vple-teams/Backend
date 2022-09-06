package com.app.vple.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @OneToMany(mappedBy = "room")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<ChattingMessage> messages;

    @Column(nullable = false, name = "show_user_a")
    private Boolean showUserA;

    @Column(nullable = false, name = "show_user_b")
    private Boolean showUserB;

    public void setChattingRoomDelete(User me) {
        if (userA == me) {
            showUserA = true;
        }
        else if (userB == me) {
            showUserB = true;
        }
    }

    @PrePersist
    public void prePersist() {
        this.showUserA = this.showUserA != null && this.showUserA;
        this.showUserB = this.showUserB != null && this.showUserB;
    }
}

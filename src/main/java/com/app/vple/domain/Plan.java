 package com.app.vple.domain;

import com.app.vple.domain.dto.PlanUpdateDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
public class Plan {

    public Plan() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plan_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private String district;

    @Column(nullable = false)
    private String city;

    private boolean isOpened;

    @Column(name = "likes_count")
    @Formula(value = "(select count(*) from check_duplicated_plan_likes where check_duplicated_plan_likes.plan_id = plan_id)")
    private Integer likesCount;

    @Column(nullable = false)
    private int peopleNum;

    @OneToMany(mappedBy = "plan")
    private List<PlanTravel> planTravels;

    @PrePersist
    public void prePersist() {
        this.likesCount = this.likesCount == null ? 0 : this.likesCount;
    }

    public void updatePlan(PlanUpdateDto planUpdateDto) {
        this.title = planUpdateDto.getTitle();
        this.startDate = planUpdateDto.getStartDate();
        this.endDate = planUpdateDto.getEndDate();
        this.isOpened = planUpdateDto.isOpened();
    }

}

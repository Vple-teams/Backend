package com.app.vple.domain.dto;

import com.app.vple.domain.Plan;
import com.app.vple.domain.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class PlanCreateDto {

    @NotBlank(message = "제목이 필요합니다.")
    private String title;

    @NotNull(message = "지역 대분류가 필요합니다.")
    private String district;

    @NotNull(message = "지역 소분류가 필요합니다.")
    private String city;

    @NotNull(message = "시작일이 필요합니다.")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull(message = "종료일이 필요합니다.")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @NotNull(message = "인원 수가 필요합니다.")
    private int peopleNum;

    public Plan toEntity(User user) {
        return Plan.builder()
                .title(title)
                .district(district)
                .city(city)
                .startDate(startDate)
                .endDate(endDate)
                .peopleNum(peopleNum)
                .user(user)
                .build();
    }
}

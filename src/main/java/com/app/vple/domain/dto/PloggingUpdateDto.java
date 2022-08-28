package com.app.vple.domain.dto;

import com.app.vple.domain.BaseTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Data
public class PloggingUpdateDto {

    @NotBlank(message = "제목이 필요합니다.")
    @Size(min = 2, max = 40, message = "제목의 길이는 최소 2부터 최대 20까지입니다.")
    private String title;

    @NotNull(message = "본문이 비어있습니다.")
    @Size(min = 2)
    private String html;

    @NotNull(message = "시작 날짜를 정해주세요")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull(message = "마감 날짜를 정해주세요")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @NotNull(message = "실행 날짜를 정해주세요")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate executionDate;

    @NotNull(message = "큰 주소를 적어주세요")
    @Size(min = 2)
    private String district;

    @NotNull(message = "작은 주소를 적어주세요")
    @Size(min = 2)
    private String city;

}

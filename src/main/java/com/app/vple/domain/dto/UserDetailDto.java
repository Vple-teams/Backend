package com.app.vple.domain.dto;

import com.app.vple.domain.User;
import com.app.vple.domain.enums.Age;
import com.app.vple.domain.enums.Gender;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserDetailDto {

    private String email;

    private String nickname;

    private Gender gender;

    private Age age;

    private String image;

    private List<MyPostsDto> posts;

    private List<MyCommentsDto> comments;

    public UserDetailDto(User user) {
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.gender = user.getGender();
        this.age = user.getAge();
        this.image = user.getImage();
        this.posts = user.getPosts().stream().map(
                MyPostsDto::new
        ).collect(Collectors.toList());
        this.comments = user.getComments().stream().map(
                MyCommentsDto::new
        ).collect(Collectors.toList());
    }
}

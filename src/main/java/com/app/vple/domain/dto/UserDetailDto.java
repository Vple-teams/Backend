package com.app.vple.domain.dto;

import com.app.vple.domain.User;
import com.app.vple.domain.enums.Age;
import com.app.vple.domain.enums.Gender;
import com.app.vple.domain.enums.VeganType;
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

    private Integer followers;

    private Integer followings;

    private Integer planCount;

    private VeganType vegetarian;

    private List<MyPostsDto> posts;

    private List<MyCommentsDto> comments;

    private String introduction;

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
        this.vegetarian = user.getVegetarian();
        this.followers = user.getFollowers();
        this.followings = user.getFollowings();
        this.introduction = user.getIntroduction();
        this.planCount = user.getPlanCount();
    }
}

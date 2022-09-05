package com.app.vple.domain.dto;

import com.app.vple.domain.User;
import com.app.vple.domain.UserFollow;
import com.app.vple.domain.enums.Age;
import com.app.vple.domain.enums.Gender;
import com.app.vple.domain.enums.VeganType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserDetailDto {

    private Long id;

    private String email;

    private String nickname;

    private Gender gender;

    private Age age;

    private String image;

    private Integer followers;

    private Integer followings;

    private Integer planCount;

    private VeganType vegetarian;

    private List<UserWrittenPlanListDto> myPlans;

    private List<Follower> followerList = new ArrayList<>();

    private List<Following> followingList = new ArrayList<>();

    private String introduction;

    public UserDetailDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.gender = user.getGender();
        this.age = user.getAge();
        this.image = user.getImage();
        this.vegetarian = user.getVegetarian();
        this.followers = user.getFollowers();
        this.followings = user.getFollowings();
        this.introduction = user.getIntroduction();
        this.planCount = user.getPlanCount();
        this.myPlans = user.getPlans().stream().map(
                UserWrittenPlanListDto::new
        ).collect(Collectors.toList());
    }

    public void setFollow(List<UserFollow> followers, List<UserFollow> followings) {
        this.followerList = followers.stream().map(
                Follower::new
        ).collect(Collectors.toList());
        this.followingList = followings.stream().map(
                Following::new
        ).collect(Collectors.toList());
    }
}

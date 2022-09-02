package com.app.vple.domain;

import com.app.vple.domain.dto.UserUpdateDto;
import com.app.vple.domain.enums.Age;
import com.app.vple.domain.enums.Gender;
import com.app.vple.domain.enums.Role;
import com.app.vple.domain.enums.VeganType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@AllArgsConstructor
@Builder
@DynamicUpdate
public class User extends BaseTime {

    public User() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, updatable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Age age;

    @Column(nullable = false, name = "my_role", updatable = false)
    @Enumerated(EnumType.STRING)
    private Role myRole;

    @URL
    @Column(name = "image_url")
    private String image;

    @OneToMany(mappedBy = "postWriter")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Post> posts;

    @OneToMany(mappedBy = "commentWriter")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Comment> comments;

    @OneToMany(mappedBy = "user")
    private List<Language> languages;

    @OneToMany(mappedBy = "user")
    private List<Plan> plans;

    @Formula(value = "(select count(*) from plan where plan.user_id=user_id)")
    private Integer planCount;

    @Formula(value = "(select count(*) from user_follow where user_follow.from_user_id=user_id)")
    private Integer followers;

    @Formula(value = "(select count(*) from user_follow where user_follow.to_user_id=user_id)")
    private Integer followings;

    @Enumerated(EnumType.STRING)
    private VeganType vegetarian;

    @Setter
    private String introduction;

    public User update(String name, String image, String age) {

        if (nickname.length() == 0) {
            this.nickname = name;
        }
        if (image.length() == 0) {
            this.image = image;
        }
        this.age = Age.toAge(age);
        return this;
    }

    public void update(UserUpdateDto updateInfo) {
        this.nickname = updateInfo.getNickname();
        this.image = updateInfo.getImage() == null ? this.image : updateInfo.getImage();
        this.gender = updateInfo.getGender() == null ? this.gender : Gender.toGender(updateInfo.getGender());
        this.age = updateInfo.getAge() == null ? this.age : Age.toAge(updateInfo.getAge());
        this.vegetarian = updateInfo.getVegetarian() == null ? this.vegetarian : VeganType.valueOf(updateInfo.getVegetarian());
    }

    public void update(String url) {
        this.image = url;
    }

    public String getRoleValue() {
        return this.myRole.getValue();
    }

}

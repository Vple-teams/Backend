package com.app.vple.service.model;

import com.app.vple.domain.User;
import com.app.vple.domain.enums.Age;
import com.app.vple.domain.enums.Gender;
import lombok.Getter;



@Getter
public class SessionLoginUser {

    private String name;

    private String email;

    private String photo;

    private Gender gender;

    private Age age;

    public SessionLoginUser(User user) {
        this.name = user.getNickname();
        this.email = user.getEmail();
        this.photo = user.getImage();
        this.gender = user.getGender();
        this.age = user.getAge();
    }

    public SessionLoginUser() {}
}

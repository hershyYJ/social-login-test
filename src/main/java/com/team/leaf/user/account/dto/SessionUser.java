package com.team.leaf.user.account.dto;

import com.team.leaf.user.account.entity.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String birthday;
    private String birthyear;
    private String phone;

    public SessionUser(User user){
        this.name = user.getName();
        this.email = user.getEmail();
        this.birthday = getBirthday();
        this.birthyear = getBirthyear();
        this.phone = getPhone();
    }
}

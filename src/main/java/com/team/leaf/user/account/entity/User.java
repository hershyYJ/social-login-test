package com.team.leaf.user.account.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String birthday;

    @Column
    private String birthyear;

    @Column
    private String phone;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountRole role;

    @Builder
    public User(String name, String email, String birthday, String birthyear, String phone, AccountRole role){
        this.name = name;
        this.email = email;
        this.birthday = birthday;
        this.birthyear = birthyear;
        this.phone = phone;
        this.role = role;
    }

    public User update(String name, String birthday, String birthyear, String phone){
        this.name = name;
        this.birthday = birthday;
        this.birthyear = birthyear;
        this.phone = phone;

        return this;
    }

    public String getRoleKey(){
        return this.role.getKey();
    }
}

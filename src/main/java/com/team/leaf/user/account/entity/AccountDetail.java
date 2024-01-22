package com.team.leaf.user.account.entity;

import com.team.leaf.user.history.entity.SearchHistory;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account_detail")
public class AccountDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    private String email;

    private String password;

    private String name;

    private String nickname;

    private String birthday;

    private String birthyear;

    private String universityName;

    private String shippingAddress;

    private String schoolAddress;

    private String workAddress;

    private LocalDate joinDate;

    private String phone;

    @Enumerated(EnumType.STRING)
    private AccountRole role;

    private LocalDate lastAccess;

    private int loginFailCount;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private AccountPrivacy userDetail;

    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<SearchHistory> searchHistories;

    public AccountDetail update(String name, String birthday, String birthyear, String phone) {
        this.name = name;
        this.birthday = birthday;
        this.birthyear = birthyear;
        this.phone = phone;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

}

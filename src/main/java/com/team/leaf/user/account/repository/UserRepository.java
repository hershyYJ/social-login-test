package com.team.leaf.user.account.repository;

import com.team.leaf.user.account.entity.AccountDetail;
import com.team.leaf.user.account.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

}

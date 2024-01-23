package com.team.leaf.user.account.repository;

import com.team.leaf.user.account.entity.AccountDetail;
import com.team.leaf.user.account.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

}

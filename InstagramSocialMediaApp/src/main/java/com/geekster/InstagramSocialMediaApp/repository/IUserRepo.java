package com.geekster.InstagramSocialMediaApp.repository;

import com.geekster.InstagramSocialMediaApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepo extends JpaRepository<User,Integer> {
    User findFirstByUserEmail(String newEmail);
}

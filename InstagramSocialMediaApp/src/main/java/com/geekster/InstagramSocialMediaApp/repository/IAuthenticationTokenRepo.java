package com.geekster.InstagramSocialMediaApp.repository;

import com.geekster.InstagramSocialMediaApp.model.AuthenticationToken;
import com.geekster.InstagramSocialMediaApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAuthenticationTokenRepo extends JpaRepository<AuthenticationToken,Integer> {

    AuthenticationToken findFirstByTokenValue(String authTokenValue);

    AuthenticationToken findFirstByUser(User user);
}

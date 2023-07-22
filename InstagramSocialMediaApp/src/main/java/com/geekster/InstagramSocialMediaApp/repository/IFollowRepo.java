package com.geekster.InstagramSocialMediaApp.repository;

import com.geekster.InstagramSocialMediaApp.model.Follow;
import com.geekster.InstagramSocialMediaApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFollowRepo extends JpaRepository<Follow,Integer> {

    List<Follow> findByCurrentUserAndCurrentUserFollower(User targetUser, User follower);
}

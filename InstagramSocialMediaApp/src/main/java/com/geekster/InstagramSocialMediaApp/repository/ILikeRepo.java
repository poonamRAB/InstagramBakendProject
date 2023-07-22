package com.geekster.InstagramSocialMediaApp.repository;

import com.geekster.InstagramSocialMediaApp.model.Like;
import com.geekster.InstagramSocialMediaApp.model.Post;
import com.geekster.InstagramSocialMediaApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ILikeRepo extends JpaRepository<Like,Integer> {

    List<Like> findByInstaPostAndLiker(Post instaPost, User liker);

    List<Like> findByInstaPost(Post validPost);
}

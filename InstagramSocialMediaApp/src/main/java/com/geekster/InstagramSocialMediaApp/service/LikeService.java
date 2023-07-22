package com.geekster.InstagramSocialMediaApp.service;

import com.geekster.InstagramSocialMediaApp.model.Like;
import com.geekster.InstagramSocialMediaApp.model.Post;
import com.geekster.InstagramSocialMediaApp.model.User;
import com.geekster.InstagramSocialMediaApp.repository.ILikeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeService {
    @Autowired
    ILikeRepo likeRepo;

    public String addLike(Like like){
        likeRepo.save(like);
        return "Instagram post liked successfully!!!";
    }

    public boolean isLikedAllowedOnThisPost(Post instaPost, User liker){
        List<Like> likeList = likeRepo.findByInstaPostAndLiker(instaPost, liker);
        return likeList != null && likeList.isEmpty();
    }

    public Integer getLikeCountForPost(Post validPost){
        return likeRepo.findByInstaPost(validPost).size();
    }

    public Like findLike(Integer likeId){
        return likeRepo.findById(likeId);
    }

    public void removeLike(Like like){
        likeRepo.delete(like);
    }

}

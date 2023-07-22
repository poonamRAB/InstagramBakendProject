package com.geekster.InstagramSocialMediaApp.service;

import com.geekster.InstagramSocialMediaApp.model.Post;
import com.geekster.InstagramSocialMediaApp.model.User;
import com.geekster.InstagramSocialMediaApp.repository.IPostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PostService {
    @Autowired
    IPostRepo postRepo;

    public String createPost(Post post){
        post.setPostCreatedTimeStamp(LocalDateTime.now());
        postRepo.save(post);
        return "Post has been successfully uploaded!!";
    }

    public String removePost(Integer postId, User user){
        Post post = postRepo.findById(postId).orElse(null);

        if(post != null && post.getPostOwner().equals(user)){
            postRepo.deleteById(postId);
            return "Post Removed Successfully!!";
        }
        else if(post == null){
             return "This post does not exist to be deleted!!";
        }
        else{
            return "Unauthorised delete detected...not allowed!!";
        }
    }

    public boolean validatePost(Post post){
        return (post!=null && postRepo.existsById(post.getPostId()));
    }

    public Post getPostById(Integer id){
        return postRepo.findById(id).orElse(null);
    }
}

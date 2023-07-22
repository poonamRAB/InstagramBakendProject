package com.geekster.InstagramSocialMediaApp.service;

import com.geekster.InstagramSocialMediaApp.model.Comment;
import com.geekster.InstagramSocialMediaApp.repository.ICommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentService {
    @Autowired
    ICommentRepo commentRepo;

    public String addComment(Comment comment){
        comment.setCommentCreationTimeStamp(LocalDateTime.now());
        commentRepo.save(comment);
        return "Comment to the post is added!!";
    }

    public Comment findComment(Integer commentId){
        return commentRepo.findById(commentId).orElse(null);
    }

    public void removeComment(Comment comment){
        commentRepo.delete(comment);
    }
}

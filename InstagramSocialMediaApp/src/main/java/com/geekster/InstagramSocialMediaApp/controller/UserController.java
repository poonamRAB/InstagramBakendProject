package com.geekster.InstagramSocialMediaApp.controller;

import com.geekster.InstagramSocialMediaApp.model.*;
import com.geekster.InstagramSocialMediaApp.model.dto.SignInInput;
import com.geekster.InstagramSocialMediaApp.model.dto.SignUpOutput;
import com.geekster.InstagramSocialMediaApp.service.AuthenticationService;
import com.geekster.InstagramSocialMediaApp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("user/signup")
    public SignUpOutput signUpInstaUser(@RequestBody User user){
        return userService.signUpUser(user);
    }

    @PostMapping("user/signIn")
    public String signInInstaUser(@RequestBody @Valid SignInInput signInInput){
        return userService.signInUser(signInInput);

    }

    @DeleteMapping("user/signOut")
    public String signOutInstaUser(String email, String token){
        if(authenticationService.authenticate(email, token)){
            return userService.signOutUser(email);
        }
        else{
            return "Sign Out not allowed for non-authenticated user!!";
        }
    }

    @PostMapping("post")
    public String createPost(@RequestBody Post post, @RequestParam String email,@RequestParam String token){
        if(authenticationService.authenticate(email, token)){
            return userService.createPost(post,email);
        }
        else{
            return "Not an authenticated user activity!!";
        }
    }

    @DeleteMapping("post")
    public String removePost(@RequestParam Integer postId, @RequestParam String email, @RequestParam String token){
        if(authenticationService.authenticate(email, token)){
            return userService.removePost(postId,email);
        }
        else{
            return "Not an authenticated user activity!!";
        }
    }

    @PostMapping("comment")
    public String addComment(@RequestBody Comment comment, @RequestParam String commenterEmail,@RequestParam String commenterToken){
        if(authenticationService.authenticate(commenterEmail, commenterToken)){
            return userService.addComment(comment,commenterEmail);
        }
        else{
            return "Not an authenticated user activity!!";
        }
    }

    @DeleteMapping("comment")
    public String removeComment(@RequestParam Integer commentId,@RequestParam String email,@RequestParam String token){
        if(authenticationService.authenticate(email, token)){
            return userService.removeComment(commentId,email);
        }
        else{
            return "Not an authenticated user activity!!";
        }
    }

    @PostMapping("like")
    public String addLike(@RequestBody Like like, @RequestParam String likeEmail,@RequestParam String likerToken){
        if(authenticationService.authenticate(likeEmail, likerToken)){
            return userService.addLike(like,likeEmail);
        }
        else{
            return "Not an authenticated user activity!!";
        }
    }

    @GetMapping("like/count/post/{postId}")
    public String getLikeCountByPost(@PathVariable Integer postId,@RequestParam String userEmail,@RequestParam String userToken){
        if(authenticationService.authenticate(userEmail, userToken)){
            return userService.getLikeCountByPost(postId,userEmail);
        }
        else{
            return "Not an authenticated user activity!!";
        }
    }

    @DeleteMapping("like")
    public String removeLike(@RequestParam Integer likeId,@RequestParam String likerEmail,@RequestParam String likerToken){
        if(authenticationService.authenticate(likerEmail, likerToken)){
            return userService.removeLike(likeId,likerEmail);
        }
        else{
            return "Not an authenticated user activity!!";
        }
    }

    @PostMapping("follow")
    public String followUser(@RequestBody Follow follow, @RequestParam String followerEmail,@RequestParam String followerToken){
        if(authenticationService.authenticate(followerEmail, followerToken)){
            return userService.followUser(follow,followerEmail);
        }
        else{
            return "Not an authenticated user activity!!";
        }
    }

    @PostMapping("follow")
    public String unFollowUser(@PathVariable Integer followId, @RequestParam String followerEmail,@RequestParam String followerToken){
        if(authenticationService.authenticate(followerEmail, followerToken)){
            return userService.unFollowUser(followId,followerEmail);
        }
        else{
            return "Not an authenticated user activity!!";
        }
    }


}

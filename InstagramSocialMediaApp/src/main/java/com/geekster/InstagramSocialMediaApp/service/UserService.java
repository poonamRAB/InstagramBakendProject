package com.geekster.InstagramSocialMediaApp.service;

import com.geekster.InstagramSocialMediaApp.model.*;
import com.geekster.InstagramSocialMediaApp.model.dto.SignInInput;
import com.geekster.InstagramSocialMediaApp.model.dto.SignUpOutput;
import com.geekster.InstagramSocialMediaApp.repository.IUserRepo;
import com.geekster.InstagramSocialMediaApp.service.utility.emailUtility.EmailHandler;
import com.geekster.InstagramSocialMediaApp.service.utility.hashingUtility.PasswordEncrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    IUserRepo userRepo;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    PostService postService;

    @Autowired
    LikeService likeService;

    @Autowired
    CommentService commentService;

    @Autowired
    FollowService followService;


    public SignUpOutput signUpUser(User user) {
        boolean signUpStatus = true;
        String signUpMsg = null;

        String newEmail = user.getUserEmail();

        if(newEmail == null){
            signUpMsg = "Invalid Email";
            signUpStatus = false;

            return new SignUpOutput(signUpStatus,signUpMsg);
        }

        User existingUser = userRepo.findFirstByUserEmail(newEmail);

        if(existingUser != null){
            signUpMsg = "This email already registered!!!";
            signUpStatus = false;

            return new SignUpOutput(signUpStatus,signUpMsg);

        }

        try{
            String encryptedPassword = PasswordEncrypter.encryptPassword(user.getUserPassword());

            user.setUserPassword(encryptedPassword);
            userRepo.save(user);

            return new SignUpOutput(signUpStatus,"User resitered successfully!");
        }
        catch(Exception e){
            signUpMsg = "Internal error occured during sign up, try again!!";
            signUpStatus = false;

            return new SignUpOutput(signUpStatus,signUpMsg);
        }
    }

    public String signInUser(SignInInput signInInput) {
        String signInMsg = null;
        String signInEmail = signInInput.getEmail();

        if(signInEmail == null){
            signInMsg = "Invalid email";

            return signInMsg;
        }

        User existingUser = userRepo.findFirstByUserEmail(signInEmail);

        if(existingUser != null){
            signInMsg = "This email already registered!!!";
            

            return signInMsg;

        }

        try{
            String encryptedPassword = PasswordEncrypter.encryptPassword(signInInput.getPassword());
            
            if(existingUser.getUserPassword().equals(encryptedPassword)){
                AuthenticationToken authToken = new AuthenticationToken(existingUser);
                authenticationService.saveToken(authToken);

                EmailHandler.sendEmail("poonamapchundakar56@gmail.com","email testing",authToken.getTokenValue());
                return "Token sent to your email";
            }
            else{
                signInMsg = "Invalid credentials!!";
                return signInMsg;
            }
            
        }
        catch(Exception e){
            signInMsg = "Internal error occurred during sign in, try again!!";
            
            return signInMsg;
        }
    }

    public String signOutUser(String email) {
        User user = userRepo.findFirstByUserEmail(email);
        AuthenticationToken token = authenticationService.findFirstByUser(user);
        authenticationService.removeToken(token);
        return "User signed out successfully!!";
    }

    public String createPost(Post post, String email) {
        User postOwner = userRepo.findFirstByUserEmail(email);
        post.setPostOwner(postOwner);
        return postService.createPost(post);
    }

    public String removePost(Integer postId, String email) {
        User user =userRepo.findFirstByUserEmail(email);
        return postService.removePost(postId,user);
    }

    public String addComment(Comment comment, String commenterEmail) {

        boolean postValid = postService.validatePost(comment.getInstaPost());

        if(postValid){
            User commenter = userRepo.findFirstByUserEmail(commenterEmail);
            comment.setCommenter(commenter);
            return commentService.addComment(comment);
        }
        else{
            return "Cannot comment on Invalid post!!";
        }
    }

    boolean authorizeCommentRemover(String email, Comment comment){
        String commentOwnerEmail = comment.getCommenter().getUserEmail();
        String postOwnerEmail = comment.getInstaPost().getPostOwner().getUserEmail();

        return postOwnerEmail.equals(email) || commentOwnerEmail.equals(email);
    }

    public String removeComment(Integer commentId, String email) {
        Comment comment = commentService.findComment(commentId);

        if(comment != null){
            if(authorizeCommentRemover(email,comment)){
                commentService.removeComment(comment);

                return "Comment deleted successfully!!";
            }
            else{
                return "Unauthorized delete detected...Not allowed!!";
            }
        }
        else{
            return "Invalid comment!!";
        }
    }

    public String addLike(Like like, String likeEmail) {

        Post post = like.getInstaPost();
        boolean postValid = postService.validatePost(post);
         if(postValid){
             User liker = userRepo.findFirstByUserEmail(likeEmail);

             if(likeService.isLikedAllowedOnThisPost(post,liker)){
                 like.setLiker(liker);
                 return likeService.addLike(like);
             }
             else{
                 return "You have already liked this post!!";
             }
         }
         else{
             return "post does not exists!!";
         }
    }

    public String getLikeCountByPost(Integer postId, String userEmail) {
         Post validPost = postService.getPostById(postId);

         if(validPost != null){
             Integer likeCount = likeService.getLikeCountForPost(validPost);
             return String.valueOf(likeCount);
         }
         else{
             return "Post not available!!";
         }
    }

    private boolean authorizeLikeRemover(String potentialLikeRemover,Like like){
        String likeOwnerEmail = like.getLiker().getUserEmail();
        return potentialLikeRemover.equals(likeOwnerEmail);
    }

    public String removeLike(Integer likeId, String likerEmail) {
        Like like = likeService.findLike(likeId);

        if(like != null){
            if(authorizeLikeRemover(likerEmail,like)){
                likeService.removeLike(like);
                return "post unliked!!";
            }
            else{
                return "Unauthorized...not allowed!!";
            }
        }
        else{
            return "post doesn't available!!";
        }
    }

    public String followUser(Follow follow, String followerEmail) {
        User followTargetUser = userRepo.findById(follow.getCurrentUser().getUserId()).orElse(null);
        User follower = userRepo.findFirstByUserEmail(followerEmail);

        if(followTargetUser != null){
            if(followService.isFollowAllowed(followTargetUser,follower)){
                followService.startFollowing(follow,follower);
                return follower.getUserHandle() + " is now following " + followTargetUser.getUserHandle();

            }
            else {
                return follower.getUserHandle() + " already follows " + followTargetUser.getUserHandle();
            }
        }
        else{
            return "Invalid user!!";
        }
    }

    private boolean authorizeUnFollow(String email, Follow follow){
        String targetEmail = follow.getCurrentUser().getUserEmail();
        String followerEmail = follow.getCurrentUserFollower().getUserEmail();

        return targetEmail.equals(email) || followerEmail.equals(email);
    }

    public String unFollowUser(Integer followId, String followerEmail) {

        Follow follow = followService.findFollow(followId);

        if(follow != null){
            if(authorizeUnFollow(followerEmail,follow)){
                followService.unfollow(follow);
                return follow.getCurrentUser().getUserHandle() + " not followed by " + followerEmail;
            }
            else{
                return "Unauthorized user not allowed to unfollow!!";
            }
        }
        else{
            return "Invalid follow mapping!!";
        }
    }
}

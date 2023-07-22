# InstagramBackendProject

-------
## Framework and Language used
  + SpringBoot
  + JAVA
  + SQL
-------
## Data Flow
  + controller
      - AdminController
      - UserController
  + model
       + dto
           - SignInInput
           - SignUpOutput
       + enums
           - AccountType
           - Gender
           - PostType
       + Admin
       + AuthenticationToken
       + Comment
       + Follow
       + Like
       + Post
       + User
   + repository
      - IAdminRepo
      - IAuthenticationTokenRepo
      - ICommentRepo
      - IFollowRepo
      - ILikeRepo
      - IPostRepo
      - IUserRepo
  + service
      - AdminService
      - AuthenticationService
      - CommentService
      - FollowService
      - LikeService
      - PostService
      - UserService
  + InstgramSocialMediaApp(main class)
---------
## Data Structure used
  + List

---------
## Project Summary 
  This project is a Backend design of Instagram social media application. Generally people use social media apps for the entertainmaint,
  it also can be used for to start business. Lots of users use this application to explore their business online, so other users can get to know about them.
  In this application, I have added some basic functions like creating post, liking other's post, do comment on other user's post. Also user will be able 
  follow other users. User can choose their account type , wheather they want to be a public figure or just want to use for personal so it will be private account.
  The users with Private account, won't be able to post publically. Only the users whoes following them can hit comment, like on that user's post.
  User's with public account, their post like reels,stories will be publical. Anyone can like or comment on their post. Also while posting instagram post user
  can add location, caption, time etc with their post.
  

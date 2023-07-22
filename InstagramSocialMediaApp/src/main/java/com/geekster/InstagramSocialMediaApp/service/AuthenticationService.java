package com.geekster.InstagramSocialMediaApp.service;

import com.geekster.InstagramSocialMediaApp.model.AuthenticationToken;
import com.geekster.InstagramSocialMediaApp.model.User;
import com.geekster.InstagramSocialMediaApp.repository.IAuthenticationTokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    IAuthenticationTokenRepo tokenRepo;

    public boolean authenticate(String email, String authTokenValue){
        AuthenticationToken authToken = tokenRepo.findFirstByTokenValue(authTokenValue);

        if(authToken == null){
            return false;
        }

        String tokenConnectEmail = authToken.getUser().getUserEmail();
        return tokenConnectEmail.equals(email);

    }

    public void removeToken(AuthenticationToken token){
        tokenRepo.delete(token);
    }

    public void saveToken(AuthenticationToken token){
        tokenRepo.save(token);
    }

    public AuthenticationToken findFirstByUser(User user){
        return tokenRepo.findFirstByUser(user);
    }
}

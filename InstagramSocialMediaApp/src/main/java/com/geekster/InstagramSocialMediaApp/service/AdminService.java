package com.geekster.InstagramSocialMediaApp.service;

import com.geekster.InstagramSocialMediaApp.repository.IAdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    IAdminRepo adminRepo;
}

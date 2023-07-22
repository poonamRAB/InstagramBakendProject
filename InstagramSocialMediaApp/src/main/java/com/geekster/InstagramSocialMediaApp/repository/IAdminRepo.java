package com.geekster.InstagramSocialMediaApp.repository;

import com.geekster.InstagramSocialMediaApp.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAdminRepo extends JpaRepository<Admin,Long> {
}

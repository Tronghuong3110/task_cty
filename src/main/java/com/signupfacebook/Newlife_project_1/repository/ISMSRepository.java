package com.signupfacebook.Newlife_project_1.repository;

import com.signupfacebook.Newlife_project_1.model.entity.SmsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISMSRepository extends JpaRepository<SmsEntity, String> {
}
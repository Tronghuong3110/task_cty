package com.signupfacebook.Newlife_project_1.repository;

import com.signupfacebook.Newlife_project_1.model.entity.PhoneNumberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface IPhoneNumberRepository extends JpaRepository<PhoneNumberEntity, String> {
    List<PhoneNumberEntity> findAllByListSim_Id(String listSimId);
}

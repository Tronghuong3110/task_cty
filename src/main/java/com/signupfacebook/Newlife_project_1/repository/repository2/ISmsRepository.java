package com.signupfacebook.Newlife_project_1.repository.repository2;

import com.signupfacebook.Newlife_project_1.model.entity2.SmsEntity2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("companyEntityManagerFactory")
public interface ISmsRepository extends JpaRepository<SmsEntity2, String> {
}

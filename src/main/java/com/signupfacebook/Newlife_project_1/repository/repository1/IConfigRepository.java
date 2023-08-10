package com.signupfacebook.Newlife_project_1.repository.repository1;

import com.signupfacebook.Newlife_project_1.model.entity1.ConfigEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("employeeEntityManagerFactory")
public interface IConfigRepository extends JpaRepository<ConfigEntity, Long>{
}

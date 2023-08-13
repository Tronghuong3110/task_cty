package com.signupfacebook.Newlife_project_1.repository.repository1;

import com.signupfacebook.Newlife_project_1.model.entity1.ConfigEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
@Qualifier("employeeEntityManagerFactory")
public interface IConfigRepository extends JpaRepository<ConfigEntity, Long>{
    @Query(value = "select * from config where id_list_sim = :idListSim", nativeQuery = true)
    List<ConfigEntity> findById_List_Sim(@Param("idListSim") String idListSim);
}

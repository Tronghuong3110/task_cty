package com.signupfacebook.Newlife_project_1.repository;

import com.signupfacebook.Newlife_project_1.model.entity.ListSimEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface IListSimRepository extends JpaRepository<ListSimEntity, String> {
    Optional<ListSimEntity> findByIdAndStatus(String id, Integer status);
    List<ListSimEntity> findAllByStatus(Integer status);
}

package com.signupfacebook.Newlife_project_1.service;

import com.signupfacebook.Newlife_project_1.model.dto.ConfigDto;
import com.signupfacebook.Newlife_project_1.model.entity1.ConfigEntity;

import java.util.*;

public interface IConfigService {
    String save(String profilePath, String idListSim, Long idConfig);
    ConfigEntity findById(Long id);
    List<ConfigDto> findAll();
}

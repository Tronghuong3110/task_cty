package com.signupfacebook.Newlife_project_1.service;

import com.signupfacebook.Newlife_project_1.model.entity.ConfigEntity;

public interface IConfigService {
    String save(String profilePath, String idListSim);
    ConfigEntity findById(String id);
}

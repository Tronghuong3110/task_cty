package com.signupfacebook.Newlife_project_1.service.impl;

import com.signupfacebook.Newlife_project_1.model.entity.ConfigEntity;
import com.signupfacebook.Newlife_project_1.model.entity.ListSimEntity;
import com.signupfacebook.Newlife_project_1.repository.IConfigRepository;
import com.signupfacebook.Newlife_project_1.repository.IListSimRepository;
import com.signupfacebook.Newlife_project_1.service.IConfigService;
import com.signupfacebook.Newlife_project_1.util.GenericUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigService implements IConfigService {

    @Autowired
    private IListSimRepository listSimRepository;
    @Autowired
    private IConfigRepository configRepository;

    @Override
    public String save(String profilePath, String idListSim) {
        ConfigEntity configEntity = new ConfigEntity();
        configEntity.setId(GenericUtil.gennericId());
        configEntity.setProfilePath(profilePath);
        ListSimEntity listSimEntity = listSimRepository.findById(idListSim)
                .orElse(null);
        configEntity.setListSimEntity(listSimEntity);
        configEntity = configRepository.save(configEntity);
        return configEntity.getId();
    }

    @Override
    public ConfigEntity findById(String id) {
        ConfigEntity configEntity = configRepository.findById(id)
                .orElse(null);
        return configEntity;
    }
}

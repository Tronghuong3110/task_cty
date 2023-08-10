package com.signupfacebook.Newlife_project_1.service.impl;

import com.signupfacebook.Newlife_project_1.model.dto.ConfigDto;
import com.signupfacebook.Newlife_project_1.model.entity1.ConfigEntity;
import com.signupfacebook.Newlife_project_1.model.entity1.ListSimEntity;
import com.signupfacebook.Newlife_project_1.repository.repository1.IConfigRepository;
import com.signupfacebook.Newlife_project_1.repository.repository1.IListSimRepository;
import com.signupfacebook.Newlife_project_1.service.IConfigService;
import com.signupfacebook.Newlife_project_1.util.GenericUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ConfigService implements IConfigService {

    @Autowired
    private IListSimRepository listSimRepository;
    @Autowired
    private IConfigRepository configRepository;

    @Override
    public String save(String profilePath, String idListSim, Long idConfig) {
        if(!profilePath.equals("")) { // case add new profile
            return saveProfilePath(profilePath);
        }
        // case add list sim to table config
        return updateProfile(idListSim, idConfig);
    }

    @Override
    public ConfigEntity findById(Long id) {
        ConfigEntity configEntity = configRepository.findById(id)
                .orElse(null);
        return configEntity;
    }

    @Override
    public List<ConfigDto> findAll() {
        try {
            List<ConfigEntity> configs = configRepository.findAll();
            List<ConfigDto> results = new ArrayList<>();
            for(ConfigEntity configEntity : configs) {
                ConfigDto configDto = new ConfigDto();
                configDto.setId(configEntity.getId());
                configDto.setProfilePath(configEntity.getProfilePath());
                results.add(configDto);
            }
            return results;
        }
        catch(Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    // case add new profile
    private String saveProfilePath(String profilePath) {
        try {
            ConfigEntity configEntity = new ConfigEntity();
            configEntity.setProfilePath(profilePath);
            configRepository.save(configEntity);
        }
        catch (Exception e) {
            e.printStackTrace();
            return "Can not save profile " + profilePath;
        }
        return "Save " + profilePath + "Success";
    }

    // case chose list sim and profile to run project
    private String updateProfile(String idListSim, Long idConfig) {
        // find one config entity from database
        ConfigEntity configEntity = configRepository.findById(idConfig)
                .orElse(null);
        ListSimEntity listSimEntity = listSimRepository.findByIdAndStatus(idListSim, 1)
                .orElse(null);
        try {
            configEntity.setListSimEntity(listSimEntity);
            configRepository.save(configEntity);
            return configEntity.getId().toString();
        }
        catch (NullPointerException e) {
            e.printStackTrace();
            return "Can not found profile with id = " + idConfig;
        }
    }
}

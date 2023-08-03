package com.signupfacebook.Newlife_project_1.controller;

import com.signupfacebook.Newlife_project_1.service.IConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class ConfigController {

    @Autowired
    private IConfigService configService;

    @PostMapping("/config")
    public String saveConfig(@RequestParam("profilePath") String profilePath,
                             @RequestParam("idListSim") String idListSim) {
        String id = configService.save(profilePath, idListSim);
        return id;
    }
}

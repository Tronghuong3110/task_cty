package com.signupfacebook.Newlife_project_1.controller;

import com.signupfacebook.Newlife_project_1.model.dto.ConfigDto;
import com.signupfacebook.Newlife_project_1.service.IConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class ConfigController {

    @Autowired
    private IConfigService configService;

    @PostMapping("/config")
    public String saveConfig(@RequestParam("profilePath") Long idProfile,
                             @RequestParam("idListSim") String idListSim,
                             @RequestParam("newProfilePath") String newProfilePath) {
        String message = configService.save(newProfilePath, idListSim, idProfile);
        return message;
    }

    @GetMapping("/profiles")
    public List<ConfigDto> findAll() {
        List<ConfigDto> results = configService.findAll();
        return results;
    }
}

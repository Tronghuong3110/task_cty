package com.signupfacebook.Newlife_project_1.controller;

import com.signupfacebook.Newlife_project_1.model.dto.ProgressData;
import com.signupfacebook.Newlife_project_1.model.entity.ConfigEntity;
import com.signupfacebook.Newlife_project_1.model.entity.PhoneNumberEntity;
import com.signupfacebook.Newlife_project_1.service.IConfigService;
import com.signupfacebook.Newlife_project_1.service.IPhoneNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RequestMapping(value = "api")
@RestController
@CrossOrigin(origins = "*")
public class HomeController {

    private final String PYTHON_API = "http://127.0.0.1:8000/api/";
    private ProgressData progressData;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private IPhoneNumberService phoneNumberService;
    @Autowired
    private IConfigService configService;

    @GetMapping("/play")
    public String callPythonApi(@RequestParam("idConfig") String idConfig){
        ConfigEntity configEntity = configService.findById(idConfig);
        if(configEntity != null) {
            List<Integer> listPhoneNumber = new ArrayList<>();
            List<PhoneNumberEntity> numberList = configEntity.getListSimEntity().getListPhoneNumber();
            for(PhoneNumberEntity phoneNumberEntity : numberList) {
                listPhoneNumber.add(phoneNumberEntity.getPhoneNumber());
            }
            String urlApi = PYTHON_API + "?profilePath=" + configEntity.getProfilePath() + "&listPhoneNumber=" + listPhoneNumber.toString();
            ResponseEntity<Object> res = restTemplate.getForEntity(urlApi, Object.class);
            return res.toString();
        }
        return null;
    }

    @PostMapping("/progress")
    public void seviceProgress(@RequestBody ProgressData data) {
        progressData = data;
        System.out.print("Current PhoneNumber = " + progressData.getCurrent_phoneNumber());
    }

    @GetMapping("/progress_current")
    public ProgressData progress_current() {
        return progressData;
    }

}

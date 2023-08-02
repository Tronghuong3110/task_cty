package com.signupfacebook.Newlife_project_1.controller;

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
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/play")
    public String callPythonApi() {
        List<String> listPhoneNumber = new ArrayList<>();
        listPhoneNumber.add("0766237972");
        listPhoneNumber.add("0769078811");
        String profile_path = "C:/Users/ASUS/.hidemyacc/profiles/profile 2";
        String urlApi = PYTHON_API + "?profilePath=" + profile_path + "&listPhoneNumber=" + listPhoneNumber.toString();
        ResponseEntity<String> res = restTemplate.getForEntity(urlApi, String.class);
        return res.toString();
    }

}

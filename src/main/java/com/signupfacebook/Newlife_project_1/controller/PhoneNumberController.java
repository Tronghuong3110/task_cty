package com.signupfacebook.Newlife_project_1.controller;

import com.signupfacebook.Newlife_project_1.model.dto.PhoneNumberDto;
import com.signupfacebook.Newlife_project_1.model.entity.ListSimEntity;
import com.signupfacebook.Newlife_project_1.service.IPhoneNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class PhoneNumberController {

    @Autowired
    private IPhoneNumberService phoneNumberService;

    @PostMapping("/file")
    public ListSimEntity importFile(@RequestParam("file") MultipartFile file){
        ListSimEntity listSimEntity = phoneNumberService.ReadDataInExcelFile(file);
        return listSimEntity;
    }

    @GetMapping("/phoneNumber/list")
    public List<PhoneNumberDto> findAll(@RequestParam("id") String id) {
        return phoneNumberService.findAllPhoneNumberByListSimId(id);
    }

    @DeleteMapping("/phoneNumber")
    public void deleteListSim(@RequestHeader List<String> ids) {
        for(String id : ids) {
            System.out.println(id);
        }
    }
}

package com.signupfacebook.Newlife_project_1.controller;

import com.signupfacebook.Newlife_project_1.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class PhoneNumberController {

    @Autowired
    private IFileService fileService;

    @PostMapping("/file")
    public String importFile(@RequestParam("file") MultipartFile file){
        fileService.ReadDataInExcelFile(file);
        return "success";
    }
}

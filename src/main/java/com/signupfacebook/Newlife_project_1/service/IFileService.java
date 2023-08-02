package com.signupfacebook.Newlife_project_1.service;

import org.springframework.web.multipart.MultipartFile;

public interface IFileService {
    void ReadDataInExcelFile(MultipartFile file);
}

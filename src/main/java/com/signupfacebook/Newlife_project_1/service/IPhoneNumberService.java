package com.signupfacebook.Newlife_project_1.service;

import com.signupfacebook.Newlife_project_1.model.dto.PhoneNumberDto;
import com.signupfacebook.Newlife_project_1.model.entity.ListSimEntity;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;

public interface IPhoneNumberService {
    ListSimEntity ReadDataInExcelFile(MultipartFile file);
    List<PhoneNumberDto> findAllPhoneNumberByListSimId(String listSimId);
}

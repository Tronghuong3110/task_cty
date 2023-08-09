package com.signupfacebook.Newlife_project_1.service;

import com.signupfacebook.Newlife_project_1.model.dto.ListSimDto;
import com.signupfacebook.Newlife_project_1.model.dto.PhoneNumberDto;
import com.signupfacebook.Newlife_project_1.model.entity1.ListSimEntity;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;

public interface IPhoneNumberService {
    ListSimEntity ReadDataInExcelFile(MultipartFile file, String name);
    List<PhoneNumberDto> findAllPhoneNumberByListSimId(String listSimId);
    String deleteListSim(List<String> ids);
    List<ListSimDto> findAllByStatus();
    String updatePhoneNumber(ListSimDto listSimDto);
    String deletePhoneNumber(List<String> ids);
}

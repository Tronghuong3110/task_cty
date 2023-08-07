package com.signupfacebook.Newlife_project_1.service;

import com.signupfacebook.Newlife_project_1.model.dto.ProcessData;

public interface IProcessService {
    ProcessData sendProcess(ProcessData data, Integer totalPhoneNumber);

}

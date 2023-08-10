package com.signupfacebook.Newlife_project_1.service.impl;

import com.signupfacebook.Newlife_project_1.model.dto.ProcessData;
import com.signupfacebook.Newlife_project_1.service.IProcessService;
import org.springframework.stereotype.Service;

@Service
public class ProcessService implements IProcessService {

    @Override
    public ProcessData sendProcess(ProcessData data, Integer totalPhoneNumber) {
        try {
            double percent = ((double)data.getIndex() / totalPhoneNumber) * 100;
            percent = Math.floor(percent * 10) / 10;
            String process = percent + "%";
            data.setProcess(process);
            return data;
        }
        catch (NullPointerException nul) {
            nul.printStackTrace();
            return new ProcessData();
        }
    }
}

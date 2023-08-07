package com.signupfacebook.Newlife_project_1.controller;

import com.signupfacebook.Newlife_project_1.model.dto.ProcessData;
import com.signupfacebook.Newlife_project_1.model.entity.ConfigEntity;
import com.signupfacebook.Newlife_project_1.model.entity.PhoneNumberEntity;
import com.signupfacebook.Newlife_project_1.service.IConfigService;
import com.signupfacebook.Newlife_project_1.service.IProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RequestMapping(value = "api")
@RestController
@CrossOrigin(origins = "*")
public class HomeController {

    private final String PYTHON_API_START = "http://127.0.0.1:8000/api/start";
    private final String PYTHON_API_FINISH = "http://127.0.0.1:8000/api/finish";
    private final String PYTHON_API_ACTION = "http://127.0.0.1:8000/api/action/";
    private ProcessData processData = null;
    private Integer totalPhoneNumber = null;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private IConfigService configService;
    @Autowired
    private IProcessService processService;

    @GetMapping("/play") // start program
    public String callPythonApi(@RequestParam("idConfig") String idConfig){
        ConfigEntity configEntity = configService.findById(idConfig);
        try{
            List<Integer> listPhoneNumber = new ArrayList<>();
            List<PhoneNumberEntity> numberList = configEntity.getListSimEntity().getListPhoneNumber();
            for(PhoneNumberEntity phoneNumberEntity : numberList) {
                listPhoneNumber.add(phoneNumberEntity.getPhoneNumber());
            }
            totalPhoneNumber = listPhoneNumber.size();
            String urlApi = PYTHON_API_START + "?profilePath=" + configEntity.getProfilePath() + "&listPhoneNumber=" + listPhoneNumber.toString();
            ResponseEntity<Object> res = restTemplate.getForEntity(urlApi, Object.class);
            return res.toString();
        }
        catch(NullPointerException nul) {
            return "Bạn chưa thực hiện cấu hình";
        }
    }

    @PostMapping("/process") // revice progress from api python
    public void seviceProgress(@RequestBody ProcessData data) {
        setData(data);
        System.out.print("Current PhoneNumber = " + processData.getCurrent_phoneNumber());
    }

    @GetMapping("/process_current") // send progress current to client
    public ProcessData procress_current() {
        if(processData != null) {
            ProcessData response = processService.sendProcess(processData, totalPhoneNumber);
            return response;
        }
        return null;
    }

    @GetMapping("/action")
    public String action(@RequestParam("action") String action) {
        String url = PYTHON_API_ACTION + "?action=";
        if(action.equals("pause")) {
            ResponseEntity<String> response = restTemplate.getForEntity(url + action, String.class);
            return response.toString();
        }
        else if(action.equals("continue")) {
            ResponseEntity<String> response = restTemplate.getForEntity(url + action, String.class);
            return response.toString();
        }
        else {
            ResponseEntity<String> response = restTemplate.getForEntity(url + action, String.class);
        }
        return null;
    }

    private void setData(ProcessData data) {
        processData = new ProcessData();
        processData.setCurrent_phoneNumber(data.getCurrent_phoneNumber());
        processData.setIndex(data.getIndex());
        processData.setMessage(data.getMessage());
        processData.setStatus(data.getStatus());
        processData.setTotalPhoneNumber(totalPhoneNumber);
    }
}

package com.signupfacebook.Newlife_project_1.controller;

import com.signupfacebook.Newlife_project_1.model.dto.ProcessData;
import com.signupfacebook.Newlife_project_1.model.entity1.ConfigEntity;
import com.signupfacebook.Newlife_project_1.model.entity1.PhoneNumberEntity;
import com.signupfacebook.Newlife_project_1.service.IConfigService;
import com.signupfacebook.Newlife_project_1.service.IProcessService;
import com.signupfacebook.Newlife_project_1.service.ISmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RequestMapping(value = "api")
@RestController
@CrossOrigin(origins = "*")
public class HomeController {

    private final String PYTHON_API_START = "http://127.0.0.1:8000/api/start";
    private final String PYTHON_API_ACTION = "http://127.0.0.1:8000/api/action/";
    private final ExecutorService executorService = Executors.newFixedThreadPool(2);
    private ProcessData processData = null;
    private Integer totalPhoneNumber = null;
    private String time_send = null;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private IConfigService configService;
    @Autowired
    private IProcessService processService;
    @Autowired
    private ISmsService smsService;

    @GetMapping("/play") // start program
    public String callPythonApi(@RequestParam("idConfig") Long idConfig){
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

    @PostMapping("/process") // receive progress from api python
    public ResponseEntity<String> receiveProgress(@RequestBody ProcessData data) {

        CompletableFuture<Void> processingFuture = CompletableFuture.runAsync(() -> {
            // Case success and wait SMS
            setData(data);
            if(data.getStatus().equals("")) {
                try {
                    Thread.sleep(10000);
                    boolean checkSave = smsService.save(time_send, data.getCurrent_phoneNumber());
                    setData(data);
                    if(checkSave) {
                        processData.setStatus("Thành công");
                        processData.setMessage("Gửi SMS thành công");
                    }
                    else {
                        processData.setStatus("Thất bại");
                        processData.setMessage("Lỗi quá thời gian chờ");
                    }
                }
                catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            System.out.print("Current PhoneNumber = " + processData.getCurrent_phoneNumber());
        }, executorService);
        return ResponseEntity.accepted().body("Success");
    }

    @PostMapping("/time_send") //receive time start send request to facebook from api python
    public void receiveTimeSend(@RequestParam("time") String time) {
        time_send = time;
    }

    @GetMapping("/process_current") // send progress current to client
    public CompletableFuture<ResponseEntity<ProcessData>> process_current() {
        CompletableFuture<ProcessData> processDataFuture = CompletableFuture.supplyAsync(() -> {
            if(processData != null) {
                ProcessData response = processService.sendProcess(processData, totalPhoneNumber);
                return response;
            }
            return null;
        }, executorService);
        return processDataFuture.thenApply(processData -> ResponseEntity.ok(processData));
    }

    @GetMapping("/action") // pause, continue, finish
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

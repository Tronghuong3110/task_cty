package com.signupfacebook.Newlife_project_1.controller;

import com.signupfacebook.Newlife_project_1.model.dto.ListSimDto;
import com.signupfacebook.Newlife_project_1.model.dto.PhoneNumberDto;
import com.signupfacebook.Newlife_project_1.model.entity.ListSimEntity;
import com.signupfacebook.Newlife_project_1.service.IPhoneNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class PhoneNumberController {

    private final String PYTHON_API_ACTION = "http://127.0.0.1:8000/api/action/";

    @Autowired
    private IPhoneNumberService phoneNumberService;
    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/file") // import list sim from file excel
    public ListSimEntity importFile(@RequestParam("file") MultipartFile file){
        ListSimEntity listSimEntity = phoneNumberService.ReadDataInExcelFile(file);
        return listSimEntity;
    }

    @GetMapping("/phoneNumber/list") // get list phone number of list sim
    public List<PhoneNumberDto> findAll(@RequestParam("id") String id) {
        return phoneNumberService.findAllPhoneNumberByListSimId(id);
    }

    @DeleteMapping("/listSim") // delete list sim
    public String deleteListSim(@RequestHeader List<String> ids) {
        String message = phoneNumberService.deleteListSim(ids);
        return message;
    }

    @GetMapping("/listSim")
    public List<ListSimDto> findAll() {
        List<ListSimDto> results = phoneNumberService.findAllByStatus();
        return results;
    }

    @PutMapping("/listSim")
    public ResponseEntity<?> updateListSim(@RequestBody ListSimDto listSimDto) {
        String message = phoneNumberService.updatePhoneNumber(listSimDto);
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/phoneNumber")
    public ResponseEntity<String> deletePhoneNumber(@RequestHeader List<String> ids) {
        String message = phoneNumberService.deletePhoneNumber(ids);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/action")
    public String test(@RequestParam("action") String action) {
        String url_pause = PYTHON_API_ACTION + "?action=";
        if(action.equals("pause")) {
            ResponseEntity<String> response = restTemplate.getForEntity(url_pause + action, String.class);
            return response.toString();
        }
        else if(action.equals("continue")) {
            ResponseEntity<String> response = restTemplate.getForEntity(url_pause + action, String.class);
            return response.toString();
        }
        return null;
    }
}

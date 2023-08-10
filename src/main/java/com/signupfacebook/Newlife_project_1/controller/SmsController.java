package com.signupfacebook.Newlife_project_1.controller;

import com.signupfacebook.Newlife_project_1.model.dto.SmsDto;
import com.signupfacebook.Newlife_project_1.model.entity2.SmsEntity2;
import com.signupfacebook.Newlife_project_1.service.ISmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RequestMapping("/api")
@RestController
@CrossOrigin("*")
public class SmsController {

    @Autowired
    private ISmsService smsService;

    @GetMapping("/listSms")
    public List<SmsDto> findAll(@RequestParam("dateSend") String dateSend,
                                    @RequestParam("dateReceive") String dateReceive,
                                    @RequestParam("receiver") String receiver) {
        if(checkValue(dateSend, dateReceive, receiver)) {
            return smsService.findAllByDateSendAndDateReceiveAndReceiver(dateSend, dateReceive, receiver);
        }
        return smsService.findAllByDateSendOrDateReceiveOrReceiver(dateSend, dateReceive, receiver);
    }

    private boolean checkValue(String dateSend, String dateReceive, String receiver) {
        int count = 0;
        if (!dateSend.equals("")) {
            count++;
        }
        if (!dateReceive.equals("")) {
            count++;
        }
        if (!receiver.equals("")) {
            count++;
        }
        System.out.print(count);
        if (count >= 2) return true;
        return false;
    }
}

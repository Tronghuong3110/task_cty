package com.signupfacebook.Newlife_project_1.service.impl;

import com.signupfacebook.Newlife_project_1.converter.SmsConverter;
import com.signupfacebook.Newlife_project_1.model.dto.SmsDto;
import com.signupfacebook.Newlife_project_1.model.entity1.SmsEntity1;
import com.signupfacebook.Newlife_project_1.model.entity2.SmsEntity2;
import com.signupfacebook.Newlife_project_1.repository.repository1.ISMSRepository;
import com.signupfacebook.Newlife_project_1.repository.repository2.ISmsRepository;
import com.signupfacebook.Newlife_project_1.service.ISmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SmsService implements ISmsService {

    @Autowired
    private ISMSRepository smsRepository;
    @Autowired
    private ISmsRepository smsRepository2;

    @Override
    public List<SmsDto> findAllByDateSendOrDateReceiveOrReceiver(String dateSend, String dateReceive, String receiver){
        List<SmsEntity1> resultSet = smsRepository.findAllByDateSendOrDateReceiveOrReceiver(dateSend, dateReceive, receiver);
        List<SmsDto> results = new ArrayList<>();
        for(SmsEntity1 smsEntity1 : resultSet) {
            results.add(SmsConverter.toDto(smsEntity1));
        }
//        List<SmsEntity2> resultSet = smsRepository2.findAll();
        return results;
    }

    @Override
    public List<SmsDto> findAllByDateSendAndDateReceiveAndReceiver(String dateSend, String dateReceive, String receiver) {
        List<SmsEntity1> resultSet = smsRepository.findAllByDateSendAndDateReceiveAndReceiver(dateSend, dateReceive, receiver);
        List<SmsDto> results = new ArrayList<>();
        for(SmsEntity1 smsEntity1 : resultSet) {
            results.add(SmsConverter.toDto(smsEntity1));
        }
//        List<SmsEntity2> resultSet = smsRepository2.findAll();
        return results;
    }

    @Override
    public String save(Date time_send) {
        return null;
    }

}

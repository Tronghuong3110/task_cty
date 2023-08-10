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
    public boolean save(String time_send, String sender) {
        try {
            time_send = time_send.trim();
            List<SmsEntity2> listSms = smsRepository2.findBySenderIdAndReceivedTime(sender,
                    time_send.replace('-', '.')
                            .substring(0, time_send.lastIndexOf(" ")));

            List<SmsEntity1> listSmsResponse = new ArrayList<>();
            if(listSms.size() <= 0) {
                return false;
            }
            for(SmsEntity2 entity2 : listSms) {
                SmsEntity1 entity1 = new SmsEntity1();
                entity1.setDate_send(time_send);
                entity1 = SmsConverter.toEntity1(entity2, entity1);
                System.out.println("content = " + entity2.getContent());
                if(entity1 != null && entity2.getContent() != null) {
                    listSmsResponse.add(entity1);
                }
            }
            if(listSmsResponse.size() <= 0) {
                return false;
            }

            listSmsResponse = smsRepository.saveAll(listSmsResponse);
            return true;
        }
        catch (NullPointerException e ) {
            e.printStackTrace();
            return false;
        }
    }

}

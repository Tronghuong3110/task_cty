package com.signupfacebook.Newlife_project_1.service;

import com.signupfacebook.Newlife_project_1.model.dto.SmsDto;

import java.util.*;

public interface ISmsService {
    List<SmsDto> findAllByDateSendOrDateReceiveOrReceiver(String dateSend, String dateReceive, String receiver);
    List<SmsDto> findAllByDateSendAndDateReceiveAndReceiver(String dateSend, String dateReceive, String receiver);
    boolean save(String time_send, String sender);
}

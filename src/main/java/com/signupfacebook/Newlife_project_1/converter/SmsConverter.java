package com.signupfacebook.Newlife_project_1.converter;

import com.signupfacebook.Newlife_project_1.model.dto.SmsDto;
import com.signupfacebook.Newlife_project_1.model.entity1.SmsEntity1;
import com.signupfacebook.Newlife_project_1.model.entity2.SmsEntity2;
import org.modelmapper.ModelMapper;

public class SmsConverter {

    public static SmsDto toDto(SmsEntity1 smsEntity1) {
        ModelMapper modelMapper = new ModelMapper();
        SmsDto smsDto = modelMapper.map(smsEntity1, SmsDto.class);
        return smsDto;
    }

    public static SmsEntity1 toEntity1(SmsEntity2 entity2, SmsEntity1 entity1) {
        try {
            entity1.setContent(entity2.getContent());
            entity1.setId(entity2.getId());
            entity1.setReceiver(entity2.getReceivedId());
            entity1.setSender(entity2.getSenderId());
            entity1.setDate_receive(entity2.getReceivedTime());
            return entity1;
        }
        catch (NullPointerException nul) {
            return null;
        }
    }
}

package com.signupfacebook.Newlife_project_1.converter;

import com.signupfacebook.Newlife_project_1.model.dto.SmsDto;
import com.signupfacebook.Newlife_project_1.model.entity1.SmsEntity1;
import org.modelmapper.ModelMapper;

public class SmsConverter {

    public static SmsDto toDto(SmsEntity1 smsEntity1) {
        ModelMapper modelMapper = new ModelMapper();
        SmsDto smsDto = modelMapper.map(smsEntity1, SmsDto.class);
        return smsDto;
    }
}

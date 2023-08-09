package com.signupfacebook.Newlife_project_1.converter;

import com.signupfacebook.Newlife_project_1.model.dto.SmsDto;
import com.signupfacebook.Newlife_project_1.model.entity1.SmsEntity;
import org.modelmapper.ModelMapper;

public class SmsConverter {

    public static SmsDto toDto(SmsEntity smsEntity) {
        ModelMapper modelMapper = new ModelMapper();
        SmsDto smsDto = modelMapper.map(smsEntity, SmsDto.class);
        return smsDto;
    }
}

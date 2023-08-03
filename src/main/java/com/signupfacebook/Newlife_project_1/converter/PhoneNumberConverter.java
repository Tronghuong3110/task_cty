package com.signupfacebook.Newlife_project_1.converter;

import com.signupfacebook.Newlife_project_1.model.dto.PhoneNumberDto;
import com.signupfacebook.Newlife_project_1.model.entity.PhoneNumberEntity;
import org.modelmapper.ModelMapper;

public class PhoneNumberConverter {

    public static PhoneNumberDto toDto(PhoneNumberEntity phoneNumberEntity) {
        ModelMapper modelMapper = new ModelMapper();
        PhoneNumberDto phoneNumberDto = modelMapper.map(phoneNumberEntity, PhoneNumberDto.class);
        return phoneNumberDto;
    }
}

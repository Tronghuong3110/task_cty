package com.signupfacebook.Newlife_project_1.converter;

import com.signupfacebook.Newlife_project_1.model.dto.PhoneNumberDto;
import com.signupfacebook.Newlife_project_1.model.entity1.PhoneNumberEntity;
import com.signupfacebook.Newlife_project_1.util.GenericUtil;
import org.modelmapper.ModelMapper;

import java.util.Date;

public class PhoneNumberConverter {

    public static PhoneNumberDto toDto(PhoneNumberEntity phoneNumberEntity) {
        ModelMapper modelMapper = new ModelMapper();
        PhoneNumberDto phoneNumberDto = modelMapper.map(phoneNumberEntity, PhoneNumberDto.class);
        return phoneNumberDto;
    }

    public static PhoneNumberEntity toEntity(PhoneNumberDto phoneNumberDto, PhoneNumberEntity phoneNumberEntity) {
        try {
            if(!phoneNumberDto.getPhoneNumber().equals(phoneNumberEntity.getPhoneNumber())) {
                phoneNumberEntity.setPhoneNumber(phoneNumberDto.getPhoneNumber());
            }
            phoneNumberEntity.setChangeDate(new Date());
            return phoneNumberEntity;
        }
        catch(NullPointerException nu) {
            return new PhoneNumberEntity();
        }
    }

    public static PhoneNumberEntity toEntity(PhoneNumberDto phoneNumberDto) {
        PhoneNumberEntity phoneNumberEntity = new PhoneNumberEntity();
        phoneNumberEntity.setPhoneNumber(phoneNumberDto.getPhoneNumber());
        phoneNumberEntity.setId(GenericUtil.gennericId());
        phoneNumberEntity.setCreateDate(new Date());
        phoneNumberEntity.setStatus(1);
        return phoneNumberEntity;
    }
}

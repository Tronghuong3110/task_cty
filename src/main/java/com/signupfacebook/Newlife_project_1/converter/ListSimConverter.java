package com.signupfacebook.Newlife_project_1.converter;

import com.signupfacebook.Newlife_project_1.model.dto.ListSimDto;
import com.signupfacebook.Newlife_project_1.model.entity1.ListSimEntity;
import org.modelmapper.ModelMapper;

import java.util.Date;

public class ListSimConverter {

    public static ListSimDto toDto(ListSimEntity listSimEntity) {
        ModelMapper modelMapper = new ModelMapper();
        ListSimDto listSimDto = modelMapper.map(listSimEntity, ListSimDto.class);
        return listSimDto;
    }

    public static ListSimEntity toEntity(ListSimDto listSimDto, ListSimEntity listSimEntity) {
        try {
            System.out.print("Node " + listSimDto.getNote());
            System.out.print("Node " + listSimEntity.getNote());
            if (listSimDto.getNote() != null && !listSimDto.getNote().equals(listSimEntity.getNote())) {
                listSimEntity.setNote(listSimDto.getNote());
            }
            if (listSimDto.getName() != null && !listSimDto.getName().equals(listSimEntity.getName())) {
                listSimEntity.setName(listSimDto.getName());
            }
            listSimEntity.setDateChange(new Date());
            return listSimEntity;
        }
        catch(NullPointerException nu) {
            return null;
        }
    }
}

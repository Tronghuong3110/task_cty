package com.signupfacebook.Newlife_project_1.service.impl;

import com.signupfacebook.Newlife_project_1.converter.PhoneNumberConverter;
import com.signupfacebook.Newlife_project_1.model.dto.PhoneNumberDto;
import com.signupfacebook.Newlife_project_1.model.entity.ListSimEntity;
import com.signupfacebook.Newlife_project_1.model.entity.PhoneNumberEntity;
import com.signupfacebook.Newlife_project_1.repository.IListSimRepository;
import com.signupfacebook.Newlife_project_1.repository.IPhoneNumberRepository;
import com.signupfacebook.Newlife_project_1.service.IPhoneNumberService;
import com.signupfacebook.Newlife_project_1.util.GenericUtil;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PhoneNumberService implements IPhoneNumberService {

    @Autowired
    private IListSimRepository listSimRepository;
    @Autowired
    private IPhoneNumberRepository phoneNumberRepository;

    @Override
    public ListSimEntity ReadDataInExcelFile(MultipartFile file) {
        ListSimEntity listSimEntity = createListSim();
        listSimEntity = listSimRepository.save(listSimEntity);
        try {
            ArrayList<PhoneNumberEntity> listPhoneNumber = new ArrayList<>();
            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
            XSSFSheet sheet = workbook.getSheetAt(0);
            for(int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                PhoneNumberEntity phoneNumberEntity = new PhoneNumberEntity();
                XSSFRow row = sheet.getRow(i);
                phoneNumberEntity.setId(GenericUtil.gennericId());
                phoneNumberEntity.setPhoneNumber((int) row.getCell(0).getNumericCellValue());
                phoneNumberEntity.setCreateDate(new Date());
                phoneNumberEntity.setListSim(listSimEntity);
                listPhoneNumber.add(phoneNumberEntity);
            }
            phoneNumberRepository.saveAll(listPhoneNumber);
            print(listPhoneNumber);
            return listSimEntity;
        }
        catch(IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<PhoneNumberDto> findAllPhoneNumberByListSimId(String listSimId) {
        List<PhoneNumberEntity> listPhoneNumber = phoneNumberRepository.findAllByListSim_Id(listSimId);
        List<PhoneNumberDto> listResult = new ArrayList<>();
        for(PhoneNumberEntity phoneNumberEntity : listPhoneNumber) {
            listResult.add(PhoneNumberConverter.toDto(phoneNumberEntity));
        }
        return listResult;
    }

    private void print(ArrayList<PhoneNumberEntity> listPhoneNumber) {
        for(PhoneNumberEntity phone : listPhoneNumber) {
            System.out.println(phone.getId() + " " + phone.getPhoneNumber() + " " + phone.getCreateDate());
        }
    }

    private ListSimEntity createListSim() {
        ListSimEntity listSimEntity = new ListSimEntity();
        String id = GenericUtil.gennericId();
        listSimEntity.setId(id);
        listSimEntity.setDateImport(new Date());
        return listSimEntity;
    }
}

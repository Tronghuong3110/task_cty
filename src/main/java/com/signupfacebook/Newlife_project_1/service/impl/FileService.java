package com.signupfacebook.Newlife_project_1.service.impl;

import com.signupfacebook.Newlife_project_1.model.entity.PhoneNumberEntity;
import com.signupfacebook.Newlife_project_1.service.IFileService;
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

@Service
public class FileService implements IFileService {

    @Override
    public void ReadDataInExcelFile(MultipartFile file) {
        try {
            ArrayList<PhoneNumberEntity> listPhoneNumber = new ArrayList<>();
            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
            XSSFSheet sheet = workbook.getSheetAt(0);
            for(int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                PhoneNumberEntity phoneNumberEntity = new PhoneNumberEntity();
                XSSFRow row = sheet.getRow(i);
                phoneNumberEntity.setId(GenericUtil.gennericId());
                phoneNumberEntity.setPhoneNumber((int)row.getCell(0).getNumericCellValue());
                phoneNumberEntity.setCreateDate(new Date());
                listPhoneNumber.add(phoneNumberEntity);
            }
//            phoneNumberRepository.saveAll(listPhoneNumber);
            print(listPhoneNumber);
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void print(ArrayList<PhoneNumberEntity> listPhoneNumber) {
        for(PhoneNumberEntity phone : listPhoneNumber) {
            System.out.println(phone.getId() + " " + phone.getPhoneNumber() + " " + phone.getCreateDate());
        }
    }

}

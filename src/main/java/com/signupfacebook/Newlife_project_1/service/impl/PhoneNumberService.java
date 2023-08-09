package com.signupfacebook.Newlife_project_1.service.impl;

import com.signupfacebook.Newlife_project_1.converter.ListSimConverter;
import com.signupfacebook.Newlife_project_1.converter.PhoneNumberConverter;
import com.signupfacebook.Newlife_project_1.model.dto.ListSimDto;
import com.signupfacebook.Newlife_project_1.model.dto.PhoneNumberDto;
import com.signupfacebook.Newlife_project_1.model.entity1.ListSimEntity;
import com.signupfacebook.Newlife_project_1.model.entity1.PhoneNumberEntity;
import com.signupfacebook.Newlife_project_1.repository.repository1.IListSimRepository;
import com.signupfacebook.Newlife_project_1.repository.repository1.IPhoneNumberRepository;
import com.signupfacebook.Newlife_project_1.service.IPhoneNumberService;
import com.signupfacebook.Newlife_project_1.util.GenericUtil;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

//    @Autowired
//    public PhoneNumberService(@Qualifier("employeeEntityManagerFactory") IPhoneNumberRepository iPhoneNumberRepository) {
//        this.phoneNumberRepository = iPhoneNumberRepository;
//    }

    @Override
    public ListSimEntity ReadDataInExcelFile(MultipartFile file, String name) {
        ListSimEntity listSimEntity = createListSim();
        listSimEntity.setStatus(1);
        listSimEntity.setName(name);
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
                phoneNumberEntity.setStatus(1);
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

    @Override
    public String deleteListSim(List<String> ids) {
        List<ListSimEntity> listSimEntities = new ArrayList<>();
        for(String id : ids) {
            ListSimEntity listSimEntity = listSimRepository.findByIdAndStatus(id, 1)
                    .orElse(null);
            listSimEntity.setStatus(0);
            listSimEntities.add(listSimEntity);
        }
        listSimRepository.saveAll(listSimEntities);
        return "success";
    }

    @Override
    public List<ListSimDto> findAllByStatus() {
        List<ListSimEntity> listSimEntities = listSimRepository.findAllByStatus(1);
        List<ListSimDto> listResult = new ArrayList<>();
        for(ListSimEntity listSimEntity : listSimEntities) {
            ListSimDto listSimDto = ListSimConverter.toDto(listSimEntity);
            listSimDto.setTotalPhoneNumber(listSimEntity.getListPhoneNumber().size());
            listResult.add(listSimDto);
        }
        return listResult;
    }

    @Override
    public String updatePhoneNumber(ListSimDto listSimDto) {
        try {
            ListSimEntity listSimEntity = listSimRepository.findByIdAndStatus(listSimDto.getId(), 1)
                    .orElse(null);
            listSimEntity = ListSimConverter.toEntity(listSimDto, listSimEntity);
            listSimEntity = listSimRepository.save(listSimEntity);

            List<PhoneNumberDto> listPhoneNumber = listSimDto.getListPhoneNumber();
            for(PhoneNumberDto phoneNumberDto : listPhoneNumber) {
                System.out.println(phoneNumberDto.getId());
                PhoneNumberEntity phoneNumberEntity = null;
                if(phoneNumberDto.getId() == null) {
                    phoneNumberEntity = PhoneNumberConverter.toEntity(phoneNumberDto);
                    phoneNumberEntity.setListSim(listSimEntity);
                }
                else {
                    phoneNumberEntity = phoneNumberRepository.findById(phoneNumberDto.getId())
                            .orElse(null);
                    phoneNumberEntity = PhoneNumberConverter.toEntity(phoneNumberDto, phoneNumberEntity);
                }
                phoneNumberRepository.save(phoneNumberEntity);
            }
            return "Success";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "Can not change phone number of list sim " + listSimDto.getId();
        }
    }

    @Override
    public String deletePhoneNumber(List<String> ids) {
        try {
            List<PhoneNumberEntity> listPhoneNumber = new ArrayList<>();
            for(String id : ids) {
                PhoneNumberEntity phoneNumberEntity = phoneNumberRepository.findByIdAndStatus(id, 1)
                        .orElse(null);
                phoneNumberEntity.setStatus(0);
                listPhoneNumber.add(phoneNumberEntity);
            }
            phoneNumberRepository.saveAll(listPhoneNumber);
            return "success";
        }
        catch (NullPointerException nu) {
            return "Can not delete phone number";
        }
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
        listSimEntity.setStatus(1);
        return listSimEntity;
    }

}

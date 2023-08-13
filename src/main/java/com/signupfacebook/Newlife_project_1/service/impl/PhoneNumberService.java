package com.signupfacebook.Newlife_project_1.service.impl;

import com.signupfacebook.Newlife_project_1.converter.ListSimConverter;
import com.signupfacebook.Newlife_project_1.converter.PhoneNumberConverter;
import com.signupfacebook.Newlife_project_1.model.dto.ListSimDto;
import com.signupfacebook.Newlife_project_1.model.dto.PhoneNumberDto;
import com.signupfacebook.Newlife_project_1.model.entity1.ConfigEntity;
import com.signupfacebook.Newlife_project_1.model.entity1.ListSimEntity;
import com.signupfacebook.Newlife_project_1.model.entity1.PhoneNumberEntity;
import com.signupfacebook.Newlife_project_1.repository.repository1.IConfigRepository;
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
import sun.security.krb5.Config;

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
    @Autowired
    private IConfigRepository configRepository;

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
    public ListSimDto findAllPhoneNumberByListSimId(String listSimId) {
        ListSimEntity listSimEntity = listSimRepository.findByIdAndStatus(listSimId, 1)
                .orElse(null);
        return ListSimConverter.toDto(listSimEntity);
    }

    @Override
    public String deleteListSim(List<String> ids) {
        List<PhoneNumberEntity> listPhoneNumber = new ArrayList<>();
        List<ConfigEntity> listConfig = new ArrayList<>();
        for(String id : ids) {
            try {
                listPhoneNumber = phoneNumberRepository.findAllByListSim_Id(id);
                for(PhoneNumberEntity phoneNumberEntity : listPhoneNumber) {
                    phoneNumberRepository.deleteById(phoneNumberEntity.getId());
                }
                listConfig = configRepository.findById_List_Sim(id);
                for(ConfigEntity configEntity : listConfig) {
                    configRepository.deleteById(configEntity.getId());
                }
                listSimRepository.deleteById(id);
            }
            catch (Exception e) {
                e.printStackTrace();
                return "Can not delete list sim " + id;
            }
        }
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
    public List<PhoneNumberDto> updatePhoneNumber(ListSimDto listSimDto) {
        try {
            ListSimEntity listSimEntity = listSimRepository.findByIdAndStatus(listSimDto.getId(), 1)
                    .orElse(null);
            listSimEntity = ListSimConverter.toEntity(listSimDto, listSimEntity);
            listSimEntity = listSimRepository.save(listSimEntity);

            List<PhoneNumberDto> listPhoneNumber = listSimDto.getListPhoneNumber();
            List<PhoneNumberEntity> listPhoneNumberEntity = new ArrayList<>();
            if(listPhoneNumber != null) {
                for(PhoneNumberDto phoneNumberDto : listPhoneNumber) {
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
            }
            listPhoneNumberEntity = phoneNumberRepository.findAllByListSim_Id(listSimDto.getId());
            List<PhoneNumberDto> responses = new ArrayList<>();
            for(PhoneNumberEntity phoneNumberEntity : listPhoneNumberEntity) {
                responses.add(PhoneNumberConverter.toDto(phoneNumberEntity));
            }
            System.out.println("Số lượng số điện thoại: " + responses.size());
            return responses;
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public String deletePhoneNumber(List<String> ids) {
        for(String id : ids) {
            try {
                phoneNumberRepository.deleteById(id);
            }
            catch (Exception e) {
                e.printStackTrace();
                return "Can not delete phone number";
            }
        }
        return "success";
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

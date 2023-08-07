package com.signupfacebook.Newlife_project_1.model.dto;

import java.util.*;

public class ListSimDto {

    private String id;
    private Date dateImport;
    private Date dateChange;
    private Integer status;
    private String note;
    private List<PhoneNumberDto> listPhoneNumber;
    private Integer totalPhoneNumber;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDateImport() {
        return dateImport;
    }

    public void setDateImport(Date dateImport) {
        this.dateImport = dateImport;
    }

    public Date getDateChange() {
        return dateChange;
    }

    public void setDateChange(Date dateChange) {
        this.dateChange = dateChange;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getTotalPhoneNumber() {
        return totalPhoneNumber;
    }

    public void setTotalPhoneNumber(Integer totalPhoneNumber) {
        this.totalPhoneNumber = totalPhoneNumber;
    }

    public List<PhoneNumberDto> getListPhoneNumber() {
        return listPhoneNumber;
    }

    public void setListPhoneNumber(List<PhoneNumberDto> listPhoneNumber) {
        this.listPhoneNumber = listPhoneNumber;
    }
}

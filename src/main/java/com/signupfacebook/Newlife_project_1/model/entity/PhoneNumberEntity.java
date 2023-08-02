package com.signupfacebook.Newlife_project_1.model.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;

@Entity
@Table(name = "phone_number")
public class PhoneNumberEntity {

    @Id
    private String id;
    @Column(name = "phone_number")
    private Integer phoneNumber;
    @Column(name = "date_import")
    private Date createDate;
    @Column(name = "date_change")
    private Date changeDate;

//    Relation ship
    @OneToMany(mappedBy = "phoneNumberEntity")
    ArrayList<SmsEntity> listSms = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public ArrayList<SmsEntity> getListSms() {
        return listSms;
    }

    public void setListSms(ArrayList<SmsEntity> listSms) {
        this.listSms = listSms;
    }
}

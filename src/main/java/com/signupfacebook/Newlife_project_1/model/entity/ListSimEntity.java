package com.signupfacebook.Newlife_project_1.model.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Date;

@Entity
@Table(name = "listSim")
public class ListSimEntity {

    @Id
    private String id;
    @Column(name = "date_import")
    private Date dateImport;
    @Column(name = "date_change")
    private Date dateChange;

//    Relationship
    @OneToMany(mappedBy = "listSim")
    List<PhoneNumberEntity> listPhoneNumber;

    @OneToMany(mappedBy = "listSimEntity")
    List<ConfigEntity> listConfig;

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

    public List<PhoneNumberEntity> getListPhoneNumber() {
        return listPhoneNumber;
    }

    public void setListPhoneNumber(List<PhoneNumberEntity> listPhoneNumber) {
        this.listPhoneNumber = listPhoneNumber;
    }

    public List<ConfigEntity> getListConfig() {
        return listConfig;
    }

    public void setListConfig(List<ConfigEntity> listConfig) {
        this.listConfig = listConfig;
    }
}

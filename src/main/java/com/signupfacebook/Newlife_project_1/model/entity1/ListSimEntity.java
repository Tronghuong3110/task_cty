package com.signupfacebook.Newlife_project_1.model.entity1;

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
    @Column(name = "status")
    private Integer status;
    @Column(name = "note", columnDefinition = "ntext")
    private String note;
    @Column(name = "name", columnDefinition = "nvarchar(255)")
    private String name;


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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

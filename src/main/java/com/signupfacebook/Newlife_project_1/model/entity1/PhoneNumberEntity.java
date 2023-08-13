package com.signupfacebook.Newlife_project_1.model.entity1;

import javax.persistence.*;
import java.util.List;
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
    @Column(name = "status")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "list_sim_id")
    private ListSimEntity listSim;

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

    public ListSimEntity getListSim() {
        return listSim;
    }

    public void setListSim(ListSimEntity listSim) {
        this.listSim = listSim;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

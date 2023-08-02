package com.signupfacebook.Newlife_project_1.model.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Sms")
public class SmsEntity {

    @Id
    private String id;
    @Column(name = "content")
    private String content;
    @Column(name = "sender")
    private String sender;
    @Column(name = "revicer")
    private String revicer;
    @Column(name = "date_send")
    private Date date_send;
    @Column(name = "date_revice")
    private Date date_revice;

    // Relation ship
    @ManyToOne
    @JoinColumn(name = "phoneId")
    private PhoneNumberEntity phoneNumberEntity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRevicer() {
        return revicer;
    }

    public void setRevicer(String revicer) {
        this.revicer = revicer;
    }

    public Date getDate_send() {
        return date_send;
    }

    public void setDate_send(Date date_send) {
        this.date_send = date_send;
    }

    public Date getDate_revice() {
        return date_revice;
    }

    public void setDate_revice(Date date_revice) {
        this.date_revice = date_revice;
    }

    public PhoneNumberEntity getPhoneNumberEntity() {
        return phoneNumberEntity;
    }

    public void setPhoneNumberEntity(PhoneNumberEntity phoneNumberEntity) {
        this.phoneNumberEntity = phoneNumberEntity;
    }
}

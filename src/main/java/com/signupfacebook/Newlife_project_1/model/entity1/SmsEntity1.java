package com.signupfacebook.Newlife_project_1.model.entity1;

import javax.persistence.*;

@Entity
@Table(name = "sms")
public class SmsEntity1 {

    @Id
    private String id;
    @Column(name = "content")
    private String content;
    @Column(name = "sender")
    private String sender;
    @Column(name = "receiver")
    private String receiver;
    @Column(name = "date_send", columnDefinition = "varchar(50)")
    private String date_send;
    @Column(name = "date_receive", columnDefinition = "varchar(50)")
    private String date_receive;

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

    public PhoneNumberEntity getPhoneNumberEntity() {
        return phoneNumberEntity;
    }

    public void setPhoneNumberEntity(PhoneNumberEntity phoneNumberEntity) {
        this.phoneNumberEntity = phoneNumberEntity;
    }

    public String getDate_send() {
        return date_send;
    }

    public void setDate_send(String date_send) {
        this.date_send = date_send;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getDate_receive() {
        return date_receive;
    }

    public void setDate_receive(String date_receive) {
        this.date_receive = date_receive;
    }
}

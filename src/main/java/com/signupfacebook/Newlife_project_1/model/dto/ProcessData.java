package com.signupfacebook.Newlife_project_1.model.dto;

public class ProcessData {

    private String current_phoneNumber;
    private String message;
    private Integer index;
    private String status;
    private String process;
    private Integer totalPhoneNumber;

    public String getCurrent_phoneNumber() {
        return current_phoneNumber;
    }

    public void setCurrent_phoneNumber(String current_phoneNumber) {
        this.current_phoneNumber = current_phoneNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTotalPhoneNumber() {
        return totalPhoneNumber;
    }

    public void setTotalPhoneNumber(Integer totalPhoneNumber) {
        this.totalPhoneNumber = totalPhoneNumber;
    }
}

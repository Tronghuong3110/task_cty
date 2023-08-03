package com.signupfacebook.Newlife_project_1.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "config")
public class ConfigEntity {

    @Id
    private String id;
    @Column(name = "profile_path")
    private String profilePath;

    @ManyToOne
    @JoinColumn(name = "id_list_sim")
    private ListSimEntity listSimEntity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    public ListSimEntity getListSimEntity() {
        return listSimEntity;
    }

    public void setListSimEntity(ListSimEntity listSimEntity) {
        this.listSimEntity = listSimEntity;
    }
}

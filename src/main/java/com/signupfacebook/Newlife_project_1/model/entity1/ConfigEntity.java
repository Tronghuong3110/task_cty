package com.signupfacebook.Newlife_project_1.model.entity1;

import javax.persistence.*;

@Entity
@Table(name = "config")
public class ConfigEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "profile_path")
    private String profilePath;

    @ManyToOne
    @JoinColumn(name = "id_list_sim")
    private ListSimEntity listSimEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

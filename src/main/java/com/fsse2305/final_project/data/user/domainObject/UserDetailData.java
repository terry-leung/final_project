package com.fsse2305.final_project.data.user.domainObject;

import com.fsse2305.final_project.data.user.entity.UserEntity;

public class UserDetailData {
    private Integer uid;
    private String firebaseUid;
    private String email;

    public UserDetailData(UserEntity entity){
        this.uid = entity.getUid();
        this.firebaseUid = entity.getFirebaseUid();
        this.email = entity.getEmail();
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getFirebaseUid() {
        return firebaseUid;
    }

    public void setFirebaseUid(String firebaseUid) {
        this.firebaseUid = firebaseUid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

package com.lyzb.jbx.model.params;

public class ImUserInfoBody {
    private String extType;
    private String userName;

    public ImUserInfoBody(String userName) {
        this.userName = userName;
    }

    public ImUserInfoBody(String extType, String userName) {
        this.extType = extType;
        this.userName = userName;
    }

    public String getExtType() {
        return extType;
    }

    public void setExtType(String extType) {
        this.extType = extType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

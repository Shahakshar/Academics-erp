package com.academics.erp.dto;

public class RequestPasswordObj {

    private String password;

    public RequestPasswordObj() {
    }

    public RequestPasswordObj(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "RequestPasswordObj{" +
                "password='" + password + '\'' +
                '}';
    }
}

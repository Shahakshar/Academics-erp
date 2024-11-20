package com.academics.erp.DTO;

public class LoginResponseObj {

    private String token;
    private Long expiry_time;

    public String getToken() {
        return token;
    }

    public LoginResponseObj setToken(String token) {
        this.token = token;
        return this;
    }

    public Long getExpiry_time() {
        return expiry_time;
    }

    public LoginResponseObj setExpiry_time(Long expiry_time) {
        this.expiry_time = expiry_time;
        return this;
    }

    @Override
    public String toString() {
        return "LoginResponseObj{" +
                "token='" + token + '\'' +
                ", expiry_time=" + expiry_time +
                '}';
    }
}

package com.wotos.wotosuserservice.model;

public class UserJwt {

    private final String jwt;

    public UserJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}

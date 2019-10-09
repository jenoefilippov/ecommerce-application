package com.example.demo.model.requests;

import com.fasterxml.jackson.annotation.JsonProperty;


public class AuthenticationRequest {
    @JsonProperty
    String username;
    @JsonProperty
    String password;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

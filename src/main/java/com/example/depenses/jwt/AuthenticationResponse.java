package com.example.depenses.jwt;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthenticationResponse {
    @JsonProperty("token")
    private String token;


    public AuthenticationResponse(String accessToken) {
        this.token = accessToken;
        
        
    }

    public String getToken() {
        return token;
    }
}
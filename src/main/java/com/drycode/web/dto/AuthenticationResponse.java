package com.drycode.web.dto;

import com.drycode.models.User;
import lombok.Data;

@Data
public class AuthenticationResponse {

    private String token;
    private User user;

    public AuthenticationResponse() {
    }

    public AuthenticationResponse(String token, User user) {
        this.token = token;
        this.user = user;
    }

}

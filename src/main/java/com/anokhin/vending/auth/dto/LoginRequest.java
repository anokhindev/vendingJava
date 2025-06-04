package com.anokhin.vending.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequest {
    private String userName;
    private String password;

    public String getUsername() {
        return this.userName;
    }

}

package com.ashen.petcommon.model;

import lombok.Data;

import java.util.Set;

@Data
public class LoginUser {
    private Long userId;
    private String username;
    private Set<String> roles;
}

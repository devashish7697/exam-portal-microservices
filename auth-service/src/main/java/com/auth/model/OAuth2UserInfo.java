package com.auth.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OAuth2UserInfo {

    private String provider;
    private String providerId;
    private String email;
    private String name;

}

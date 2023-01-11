package com.sashkou.SpringSecurityCSRF.security;

import lombok.Data;

import java.io.Serializable;

@Data
public class Token implements Serializable {
    private long id;
    private String user;
    private String token;
}

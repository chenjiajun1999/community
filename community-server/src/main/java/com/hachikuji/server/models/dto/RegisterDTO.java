package com.hachikuji.server.models.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class RegisterDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String email;

    private String username;

    private String password;


}
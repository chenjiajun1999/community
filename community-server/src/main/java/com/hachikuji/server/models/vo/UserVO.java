package com.hachikuji.server.models.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class UserVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String username;

    private String headerUrl;

    public UserVO(String username, String headerUrl) {
        this.username = username;
        this.headerUrl = headerUrl;
    }

}

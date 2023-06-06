package com.hachikuji.server.models.vo;

import com.hachikuji.frame.pojo.Message;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class MessageVO extends Message implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer userId;

    private String headerUrl;

    private String username;

    private int unreadCount;

}
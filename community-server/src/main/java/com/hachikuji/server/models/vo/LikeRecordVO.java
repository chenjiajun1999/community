package com.hachikuji.server.models.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LikeRecordVO implements Serializable {


    private static final long serialVersionUID = 1L;

    private String likeStatus;

    private int likeCount;
}

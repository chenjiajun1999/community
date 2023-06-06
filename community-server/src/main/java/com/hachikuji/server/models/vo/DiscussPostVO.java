package com.hachikuji.server.models.vo;

import com.hachikuji.frame.pojo.DiscussPost;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class DiscussPostVO extends DiscussPost implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;

    private String headerUrl;

    private String likeStatus;

}

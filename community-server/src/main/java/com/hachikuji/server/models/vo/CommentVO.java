package com.hachikuji.server.models.vo;

import com.hachikuji.core.domain.model.DataTable;
import com.hachikuji.frame.pojo.Comment;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class CommentVO extends Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;

    private String headerUrl;

    private String targetUsername;

    private String likeStatus;

    private DataTable reply;

}
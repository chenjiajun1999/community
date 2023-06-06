package com.hachikuji.server.controller;

import com.hachikuji.core.domain.AjaxResult;
import com.hachikuji.core.domain.model.DataTable;
import com.hachikuji.server.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 创建评论
     *
     * @param entityType 评论类型
     * @param entityId   目标 id，帖子 or 回复
     * @param targetId   目标用户 id
     * @param content    评论内容
     * @return 是否成功
     */
    @PostMapping("")
    public AjaxResult add(Integer entityType, Integer entityId,
                          Integer targetId, String content) {


        commentService.add(entityType, entityId, targetId, content);
        return AjaxResult.success();

    }

    /**
     * 分页查询评论
     *
     * @param discussPostId 回复类型的 ID
     * @return 结果
     */
    @GetMapping("/{discussPostId}")
    public AjaxResult page(@PathVariable Integer discussPostId) {

        DataTable comments = commentService.pageByPostId(discussPostId);
        return AjaxResult.success(comments);
    }
}

package com.hachikuji.server.controller;


import com.hachikuji.core.domain.AjaxResult;
import com.hachikuji.core.domain.model.DataTable;
import com.hachikuji.server.models.vo.DiscussPostVO;
import com.hachikuji.server.service.DiscussPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 讨论贴控制器
 *
 * @author hachikuji
 * @since 2022-5-16
 */
@RestController
@RequestMapping("/discussPost")
public class DiscussPostController {

    @Autowired
    private DiscussPostService discussPostService;

    /**
     * 分页查询首页帖子信息
     *
     * @return 帖子及其用户信息
     */
    @GetMapping("")
    public AjaxResult page(){

        DataTable posts = discussPostService.page();
        return AjaxResult.success(posts);

    }

    /**
     * 创建帖子
     *
     * @param title   标题
     * @param content 文章
     * @return 提示信息
     */
    @PostMapping("")
    public AjaxResult add(@RequestParam String title, @RequestParam String content) {

        discussPostService.add(title, content);
        return AjaxResult.success();

    }

    /**
     * 查询帖子内容
     *
     * @param id 帖子 id
     * @return content
     */
    @GetMapping("/{id}/content")
    public AjaxResult getContentById(@PathVariable int id) {

        String content = discussPostService.getContentById(id);
        return AjaxResult.success(content);
    }


    /**
     * 查询帖子
     *
     * @param id 帖子 id
     * @return content
     */
    @GetMapping("/{id}")
    public AjaxResult getById(@PathVariable int id) {

        DiscussPostVO discussPostVO = discussPostService.getById(id);
        return AjaxResult.success(discussPostVO);
    }

}

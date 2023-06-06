package com.hachikuji.server.service;


import com.hachikuji.core.domain.model.DataTable;
import com.hachikuji.core.service.BaseService;
import com.hachikuji.frame.mapper.DiscussPostMapper;
import com.hachikuji.frame.mapper.UserMapper;
import com.hachikuji.frame.pojo.DiscussPost;
import com.hachikuji.frame.pojo.User;
import com.hachikuji.frame.security.utils.SecurityUtils;
import com.hachikuji.server.constant.ServerConstants;
import com.hachikuji.server.filter.SensitiveFilter;
import com.hachikuji.server.models.vo.DiscussPostVO;
import com.hachikuji.server.models.vo.LikeRecordVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class DiscussPostService extends BaseService {

    @Resource
    private DiscussPostMapper discussPostMapper;

    @Resource
    private UserMapper userMapper;


    @Autowired
    private SensitiveFilter sensitiveFilter;


    @Autowired
    private LikeRecordService likeRecordService;


    public DataTable page() {

        //启动分页
        startPage();
        List<DiscussPost> posts = discussPostMapper.selectList(null);
        long total = getDataTable(posts).getTotal();

        List<Object> rows = new ArrayList<>();
        //再查询
        posts.forEach(p -> {

            DiscussPostVO vo = appendExtraInfo(p);
            vo.setContent(null);
            rows.add(vo);

        });

        return getDataTable(rows, total);

    }

    public DiscussPostVO getById(int id) {

        return appendExtraInfo(discussPostMapper.selectById(id));

    }


    public void add(String title, String content) {

        // 得到登录用户的 userId，未登录过滤器直接会过滤
        int userId = SecurityUtils.getUserId();

        DiscussPost post = DiscussPost.builder()
                .userId(userId)
                .title(sensitiveFilter.filter(title))
                .content(sensitiveFilter.filter(content))
                .createTime(LocalDateTime.now()).build();

        discussPostMapper.insert(post);

    }

    public String getContentById(int id) {

        return discussPostMapper.selectById(id).getContent();

    }

    /**
     * 添加额外的信息构建 VO
     *
     * @param p pojo
     * @return vo
     */

    private DiscussPostVO appendExtraInfo(DiscussPost p) {

        DiscussPostVO vo = new DiscussPostVO();
        BeanUtils.copyProperties(p, vo);

        User u = userMapper.selectById(p.getUserId());
        vo.setUsername(u.getUsername());
        vo.setHeaderUrl(u.getHeaderUrl());

        // 读取 redis 中的值
        LikeRecordVO lvo = likeRecordService.query(ServerConstants.ENTITY_TYPE_POST, p.getId(), vo.getLikeCount());
        BeanUtils.copyProperties(lvo, vo);
        return vo;
    }

}

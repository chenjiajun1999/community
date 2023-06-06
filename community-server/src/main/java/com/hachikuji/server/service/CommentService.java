package com.hachikuji.server.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hachikuji.core.domain.model.DataTable;
import com.hachikuji.core.service.BaseService;
import com.hachikuji.core.utils.StringUtils;
import com.hachikuji.frame.mapper.CommentMapper;
import com.hachikuji.frame.mapper.DiscussPostMapper;
import com.hachikuji.frame.mapper.UserMapper;
import com.hachikuji.frame.pojo.Comment;
import com.hachikuji.frame.pojo.DiscussPost;
import com.hachikuji.frame.pojo.User;
import com.hachikuji.frame.security.utils.SecurityUtils;
import com.hachikuji.server.constant.ServerConstants;
import com.hachikuji.server.filter.SensitiveFilter;
import com.hachikuji.server.models.vo.CommentVO;
import com.hachikuji.server.models.vo.LikeRecordVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * 评论服务类
 * **********
 * 评论 entityType 代表评论回复的类型
 * entityId 表示评论回复的类型的 ID，如 Type = Post 为帖子 id / Type = Comment 为回复 id
 * targetId 表示评论目标用户 ID
 *
 * @author hachikuji
 */
@Service
public class CommentService extends BaseService {

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private DiscussPostMapper discussPostMapper;

    @Resource
    private UserMapper userMapper;

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Autowired
    private LikeRecordService likeRecordService;


    /**
     * 评论类型
     */
    public static int ENTITY_TYPE_POST = 1;
    public static int ENTITY_TYPE_COMMENT = 2;

    @Transactional(propagation = Propagation.REQUIRED)
    public void add(Integer entityType, Integer entityId, Integer targetId, String content) {

        if (!StringUtils.isNotEmpty(content))
            throw new IllegalArgumentException("参数不能为空！");

        // 得到登录用户的 userId，未登录过滤器直接会过滤
        int userId = SecurityUtils.getUserId();

        Comment comment = Comment.builder()
                .userId(userId)
                .entityType(entityType)
                .entityId(entityId)
                .targetId(targetId)
                .content(sensitiveFilter.filter(content))
                .createTime(LocalDateTime.now()).build();

        if (entityType == ENTITY_TYPE_POST) {
            Integer count = discussPostMapper.selectById(entityId).getCommentCount();
            UpdateWrapper<DiscussPost> updateWrapper = new UpdateWrapper<>();
            updateWrapper.set("comment_count", count + 1).eq("id", entityId);
            discussPostMapper.update(null, updateWrapper);
        }

        commentMapper.insert(comment);
    }

    public DataTable pageByPostId(int entityId) {

        return getByEntity(ENTITY_TYPE_POST, entityId, true);

    }

    public DataTable getByEntity(int entityType, int entityId, boolean isPage) {

        List<Object> rows = new ArrayList<>();

        // 启动分页
        if (isPage)
            startPage();
        // 查询文章评论
        List<Comment> comments = getByEntity(entityType, entityId);
        long total = getDataTable(comments).getTotal();

        // 再查询
        comments.forEach(c -> {

            CommentVO vo = new CommentVO();
            BeanUtils.copyProperties(c, vo);

            User u = userMapper.selectById(c.getUserId());
            vo.setUsername(u.getUsername());
            vo.setHeaderUrl(u.getHeaderUrl());

            // 读取 redis 中的值
            LikeRecordVO lvo = likeRecordService.query(ServerConstants.ENTITY_TYPE_COMMENT, c.getId(), vo.getLikeCount());
            BeanUtils.copyProperties(lvo, vo);

            // reply 的目标用户信息
            if (c.getTargetId() != 0)
                vo.setTargetUsername(userMapper.selectById(c.getTargetId()).getUsername());

            if (entityType == ENTITY_TYPE_POST)
                vo.setReply(getByEntity(ENTITY_TYPE_COMMENT, c.getId(), false));

            rows.add(vo);
        });

        return getDataTable(rows, total);
    }

    public List<Comment> getByEntity(int entityType, int entityId) {

        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("entity_type", entityType)
                .eq("entity_id", entityId);
        return commentMapper.selectList(queryWrapper);

    }


}

package com.hachikuji.server.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hachikuji.frame.mapper.CommentMapper;
import com.hachikuji.frame.mapper.DiscussPostMapper;
import com.hachikuji.frame.mapper.LikeRecordMapper;
import com.hachikuji.frame.pojo.Comment;
import com.hachikuji.frame.pojo.DiscussPost;
import com.hachikuji.frame.pojo.LikeRecord;
import com.hachikuji.frame.security.utils.SecurityUtils;
import com.hachikuji.frame.utils.RedisCache;
import com.hachikuji.server.constant.ServerConstants;
import com.hachikuji.server.models.vo.LikeRecordVO;
import com.hachikuji.server.utils.RedisKeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 通过使用 Redis 实现点赞
 *
 * @author hachikuji
 */
@Service
public class LikeRecordService {


    @Autowired
    RedisCache redisCache;

    @Resource
    LikeRecordMapper likeRecordMapper;

    @Resource
    DiscussPostMapper discussPostMapper;

    @Resource
    CommentMapper commentMapper;


    public final static String LIKE_RECORD = "LIKE_RECORD";
    public final static String LIKE_COUNT = "LIKE_COUNT";


    public LikeRecordVO like(int entityType, int entityId) {

        int userId = SecurityUtils.getUserId();
        // status
        String recordHKey = RedisKeyUtils.getEntityLikeRecordHKey(entityType, entityId, userId);
        // String status = redisCache.getCacheObject(recordHKey);
        String status = redisCache.getCacheMapValue(LIKE_RECORD, recordHKey);
        if (status != null)
            status = status.equals("1") ? "0" : "1";
        else {
            // 没有再查数据库
            QueryWrapper<LikeRecord> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("entity_type", entityType)
                    .eq("entity_id", entityId)
                    .eq("user_id", userId);

            int count = likeRecordMapper.selectCount(queryWrapper);
            status = count > 0 ? "0" : "1";

        }
        //redisCache.setCacheObject(recordHKey, status);
        redisCache.setCacheMapValue(LIKE_RECORD, recordHKey, status);

        // count
        String countHKey = RedisKeyUtils.getEntityLikeCountHKey(entityType, entityId);
        //String count = redisCache.getCacheObject(countHKey);
        String count = redisCache.getCacheMapValue(LIKE_COUNT, countHKey);
        int likeCount = count != null ? Integer.parseInt(count) :
                (entityType == ServerConstants.ENTITY_TYPE_POST ?
                        discussPostMapper.selectById(entityId).getLikeCount() :
                        commentMapper.selectById(entityId).getLikeCount());

        likeCount = status.equals("1") ? likeCount + 1 : likeCount - 1;
        //redisCache.setCacheObject(countHKey, Integer.toString(likeCount));
        redisCache.setCacheMapValue(LIKE_COUNT, countHKey, Integer.toString(likeCount));

        return new LikeRecordVO(status, likeCount);

    }


    // 提供查询
    public LikeRecordVO query(int entityType, int entityId, int srcCount) {

        String status;
        try {
            int userId = SecurityUtils.getUserId();
            // status
            String recordHKey = RedisKeyUtils.getEntityLikeRecordHKey(entityType, entityId, userId);
            status = redisCache.getCacheMapValue(LIKE_RECORD, recordHKey);
            if (status == null) {
                // 没有再查数据库
                QueryWrapper<LikeRecord> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("entity_type", entityType)
                        .eq("entity_id", entityId)
                        .eq("user_id", userId);

                status = likeRecordMapper.selectCount(queryWrapper) > 0 ? "1" : "0";
            }
            redisCache.setCacheMapValue(LIKE_RECORD, recordHKey, status);
        } catch (Exception e) {
            // 未登录
            status = "0";
        }

        // count
        String countHKey = RedisKeyUtils.getEntityLikeCountHKey(entityType, entityId);
        String count = redisCache.getCacheMapValue(LIKE_COUNT, countHKey);

        if (count == null) {
            redisCache.setCacheMapValue(LIKE_COUNT, countHKey, Integer.toString(srcCount));
            return new LikeRecordVO(status, srcCount);
        }

        // 查到看 redis 的
        return new LikeRecordVO(status, Integer.parseInt(count));
    }

    public void save() {

        // 保存 redis 中的点赞数量到 MySQL
        Map<String, Object> records = redisCache.getCacheMap(LIKE_RECORD);
        records.forEach((k, v) -> {

            // 遍历map保存数据
            String userId = RedisKeyUtils.getUserId(k);
            String entityId = RedisKeyUtils.getEntityId(k);
            String entityType = RedisKeyUtils.getEntityType(k);

            QueryWrapper<LikeRecord> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", userId)
                    .eq("entity_id", entityId)
                    .eq("entity_type", entityType);
            Integer count = likeRecordMapper.selectCount(queryWrapper);

            if (count > 0) {
                // 数据库中存在数据
                if (v.toString().equals("0")) {
                    // 为0表示取消点赞，删除数据库中对应的数据
                    likeRecordMapper.delete(queryWrapper);
                }
            } else {
                // 数据库中没有记录
                if (v.toString().equals("1")) {
                    // 为1表述点赞，添加数据到数据库
                    LikeRecord record = LikeRecord.builder()
                            .userId(Integer.parseInt(userId))
                            .entityType(Integer.parseInt(entityType))
                            .entityId(Integer.parseInt(entityId))
                            .createTime(LocalDateTime.now()).build();
                    likeRecordMapper.insert(record);
                }
            }
            // 删除redis中的记录
            redisCache.delCacheMapValue(LIKE_RECORD, k);
        });

        // 保存点赞总数到mysql中
        Map<String, Object> counts = redisCache.getCacheMap(LIKE_COUNT);
        counts.forEach((k, v) -> {

            // 更新点赞总数
            if (ServerConstants.ENTITY_TYPE_POST == Integer.parseInt(RedisKeyUtils.getEntityType(k))) {

                UpdateWrapper<DiscussPost> wrapper = new UpdateWrapper<>();
                wrapper.eq("id", Integer.parseInt(RedisKeyUtils.getEntityId(k)))
                        .set("like_count", Integer.parseInt(v.toString()));
                discussPostMapper.update(null, wrapper);

            }

            if (ServerConstants.ENTITY_TYPE_COMMENT == Integer.parseInt(RedisKeyUtils.getEntityType(k))) {

                UpdateWrapper<Comment> wrapper = new UpdateWrapper<>();
                wrapper.eq("id", Integer.parseInt(RedisKeyUtils.getEntityId(k)))
                        .set("like_count", Integer.parseInt(v.toString()));
                commentMapper.update(null, wrapper);

            }
            // 删除redis中对应的记录（可以不删）
            redisCache.delCacheMapValue(LIKE_COUNT, k);
        });

    }
}

package com.hachikuji.server.utils;

public class RedisKeyUtils {

    private static final String SPLIT = ":";

    private static final String PREFIX_ENTITY_LIKE_RECORD = "like_record:entity";
    private static final String PREFIX_USER_LIKE_RECORD  = "like_record:user";

    private static final String PREFIX_ENTITY_LIKE_COUNT = "like_count:entity";
    private static final String PREFIX_USER_LIKE_COUNT = "like_count:user";

    private static final String PREFIX_FOLLOWEE = "followee";
    private static final String PREFIX_FOLLOWER = "follower";

    /**
     * 用户点赞某个实体
     * like:entity:entityType:entityId:userId ->  0 or 1
     *
     * @param entityType entityType
     * @param entityId entityId
     * @return key
     */
    public static String getEntityLikeRecordHKey(int entityType, int entityId,int  userId) {
        return PREFIX_ENTITY_LIKE_RECORD + SPLIT + entityType + SPLIT + entityId +  SPLIT + userId;
    }
    public static String getEntityType(String hKey) {
        return hKey.split(SPLIT)[2];
    }
    public static String getEntityId(String hKey) {
        return hKey.split(SPLIT)[3];
    }
    public static String getUserId(String hKey) {
        return hKey.split(SPLIT)[4];
    }

    /**
     * 某个实体的赞数量
     * like:entity:entityType:entityId -> count
     *
     * @param entityType entityType
     * @param entityId entityId
     * @return key
     */
    public static String getEntityLikeCountHKey(int entityType, int entityId) {
        return PREFIX_ENTITY_LIKE_COUNT + SPLIT + entityType + SPLIT + entityId;
    }


    /**
     * 某个用户收到的赞数量
     * like:user:userId -> count
     * @param userId userId
     * @return key
     */
    public static String getUserLikeKey(int userId) {
        return PREFIX_USER_LIKE_COUNT + SPLIT + userId;
    }


    // 某个用户关注的实体
    // followee:userId:entityType -> zset(entityId,now)
    public static String getFolloweeKey(int userId, int entityType) {
        return PREFIX_FOLLOWEE + SPLIT + userId + SPLIT + entityType;
    }

    // 某个实体拥有的粉丝
    // followee:entityType:entityId -> zset(userId,now)
    public static String getFollowerKey(int entityType, int entityId) {
        return PREFIX_FOLLOWER + SPLIT + entityType + SPLIT + entityId;
    }


}

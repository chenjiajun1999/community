package com.hachikuji.core.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hachikuji.core.constant.HttpStatus;
import com.hachikuji.core.domain.model.DataTable;
import com.hachikuji.core.utils.StringUtils;

/**
 * 操作消息提醒
 *
 * @author ruoyi
 */
public class AjaxResult extends HashMap<String, Object>
{
    private static final long serialVersionUID = 1L;

    /** 状态码 */
    public static final String CODE_TAG = "code";

    /** 返回内容 */
    public static final String MSG_TAG = "msg";

    /** 数据对象 */
    public static final String DATA_TAG = "data";

    /** 表格数据对象 */
    public static final String ROWS_TAG = "rows";

    /** 表格列数 */
    public static final String TOTAL_TAG = "total";

    /**
     * 初始化一个新创建的 AjaxResult 对象，使其表示一个空消息。
     */
    public AjaxResult()
    {
    }

    /**
     * 初始化一个新创建的 AjaxResult 对象
     *
     * @param code 状态码
     * @param msg 返回内容
     */
    public AjaxResult(int code, String msg)
    {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
    }

    /**
     * 初始化一个新创建的 AjaxResult 对象
     *
     * @param code 状态码
     * @param msg 返回内容
     * @param data 数据对象
     */
    public AjaxResult(int code, String msg, Object data)
    {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        if (StringUtils.isNotNull(data))
        {
            super.put(DATA_TAG, data);
        }
    }

    /**
     * 初始化一个新创建的 AjaxResult 对象
     *
     * @param code 状态码
     * @param msg 返回内容
     * @param rows 表格数据对象
     * @param total 表格总行数
     */
    public AjaxResult(int code, String msg, List<?> rows,long total)
    {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        if (rows != null)
        {
            super.put(ROWS_TAG, rows);
            super.put(TOTAL_TAG, total);
        }
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static AjaxResult success()
    {
        return AjaxResult.success("操作成功");
    }

    /**
     * 返回成功数据
     *
     * @return 成功消息
     */
    public static AjaxResult success(Object data)
    {
        return AjaxResult.success("操作成功", data);
    }

    /**
     * 返回成功数据
     *
     * @return 成功消息
     */
    public static AjaxResult success(List<?> rows,long total)
    {
        return AjaxResult.success("操作成功", rows,total);
    }

    /**
     * 返回成功数据
     *
     * @return 成功消息
     */
    public static AjaxResult success(DataTable dataTable){

        return AjaxResult.success(dataTable.getRows(),dataTable.getTotal());

    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static AjaxResult success(String msg)
    {
        return AjaxResult.success(msg, null);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static AjaxResult success(String msg, Object data)
    {
        return new AjaxResult(HttpStatus.SUCCESS, msg, data);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @param rows 表格数据对象
     * @param total 表格总行数
     * @return 成功消息
     */
    public static AjaxResult success(String msg, List<?> rows,long total)
    {
        return new AjaxResult(HttpStatus.SUCCESS, msg, rows,total);
    }

    /**
     * 返回成功消息
     *
     * @param data 哈希表格式
     * @return 成功消息
     */
    public static AjaxResult success(Map<String ,Object> data){

        AjaxResult result =  AjaxResult.success("操作成功");

        for(String key : data.keySet())
            result.put(key,data.get(key));

        return result;
    }


    /**
     * 返回错误消息
     *
     * @return
     */
    public static AjaxResult error()
    {
        return AjaxResult.error("操作失败");
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static AjaxResult error(String msg)
    {
        return AjaxResult.error(msg, null);
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static AjaxResult error(String msg, Object data)
    {
        return new AjaxResult(HttpStatus.ERROR, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @param rows 表格数据对象
     * @param total 表格总行数
     * @return 警告消息
     */
    public static AjaxResult error(String msg,List<?> rows,long total)
    {
        return new AjaxResult(HttpStatus.ERROR, msg, rows,total);
    }

    /**
     * 返回错误消息
     *
     * @param code 状态码
     * @param msg 返回内容
     * @return 警告消息
     */
    public static AjaxResult error(int code, String msg)
    {
        return new AjaxResult(code, msg, null);
    }

    /**
     * 方便链式调用
     *
     * @param key 键
     * @param value 值
     * @return 数据对象
     */
    @Override
    public AjaxResult put(String key, Object value)
    {
        super.put(key, value);
        return this;
    }
}

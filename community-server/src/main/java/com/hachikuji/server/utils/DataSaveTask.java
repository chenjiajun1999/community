package com.hachikuji.server.utils;

import com.hachikuji.server.service.LikeRecordService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

/**
 * @author :hachikuji
 * @description :
 * @create :2022-07-21 11:23:00
 */
@Component
public class DataSaveTask extends QuartzJobBean {


    @Autowired
    LikeRecordService likeRecordService;


    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        likeRecordService.save();
    }
}

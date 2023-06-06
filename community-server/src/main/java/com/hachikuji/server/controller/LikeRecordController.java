package com.hachikuji.server.controller;


import com.hachikuji.core.domain.AjaxResult;
import com.hachikuji.server.models.vo.LikeRecordVO;
import com.hachikuji.server.service.LikeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LikeRecordController {


    @Autowired
    LikeRecordService likeRecordService;




    @PostMapping("/like")
    public AjaxResult like(int entityType, int entityId){

        LikeRecordVO result = likeRecordService.like(entityType, entityId);
        return AjaxResult.success(result);
    }


}

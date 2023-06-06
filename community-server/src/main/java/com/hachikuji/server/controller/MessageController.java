package com.hachikuji.server.controller;


import com.hachikuji.core.domain.AjaxResult;
import com.hachikuji.core.domain.model.DataTable;
import com.hachikuji.server.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * 消息控制器
 *
 * @author hachikuji
 * @since 2022-5-16
 */
@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    MessageService messageService;


    @GetMapping("")
    public AjaxResult pageChats() {

        Map<String, Object> chats = messageService.pageChats();
        return AjaxResult.success(chats);
    }

    @GetMapping("/notice")
    public AjaxResult getNotices() {

        return AjaxResult.success();
    }

    @PostMapping("")
    public AjaxResult add(@RequestParam int id, @RequestParam String message) {

        messageService.add(id, message);
        return AjaxResult.success();

    }

    @PutMapping("/{id}")
    public AjaxResult updateByToId(@PathVariable int id) {

        messageService.updateStateByToId(id);
        return AjaxResult.success() ;

    }

    @GetMapping("/{toId}")
    public AjaxResult pageByToId(@PathVariable Integer toId) {

        DataTable messages = messageService.pageByToId(toId);
        return AjaxResult.success(messages);
    }

}

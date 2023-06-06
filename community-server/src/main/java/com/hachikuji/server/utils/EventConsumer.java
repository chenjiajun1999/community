package com.hachikuji.server.utils;


import com.hachikuji.frame.mapper.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventConsumer {

    @Autowired
    MessageMapper messageMapper;
}

package com.hachikuji.frame.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;


@Data
@NoArgsConstructor
public class Event {

    private String topic;
    private int userId;
    private int entityType;
    private int entityId;
    private Map<String,Object> data =  new HashMap<>();
}

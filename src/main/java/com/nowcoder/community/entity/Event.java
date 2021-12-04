package com.nowcoder.community.entity;

import java.util.HashMap;
import java.util.Map;

public class Event {

    private String topic;
    private int userId;
    private int entityType;
    private int entityId;
    private int entityUserId;
    private Map<String, Object> data = new HashMap<>();
    //我们可能还有其他的功能需要用到事件，但现在不清楚需要什么数据，因此用一个map，使这个实体具有一定扩展性

    public String getTopic() {
        return topic;
    }

    //作用见commentController 触发评论事件
    //这样把set的返回值改成event是为了使用方便，可以用返回的event来set其他属性
    // (以后会处理很多事件，你就知道这样的便捷之处了)
    //它的作用是set值，那为什么不用构造器呢，因为可能我们只需要其中的一部分值，用这种方式就可以需要什么就set什么
    public Event setTopic(String topic) {
        this.topic = topic;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public Event setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public int getEntityType() {
        return entityType;
    }

    public Event setEntityType(int entityType) {
        this.entityType = entityType;
        return this;
    }

    public int getEntityId() {
        return entityId;
    }

    public Event setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    public int getEntityUserId() {
        return entityUserId;
    }

    public Event setEntityUserId(int entityUserId) {
        this.entityUserId = entityUserId;
        return this;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public Event setData(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

}
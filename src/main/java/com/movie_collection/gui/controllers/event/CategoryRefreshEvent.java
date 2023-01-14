package com.movie_collection.gui.controllers.event;

import com.movie_collection.bll.helpers.EventType;

public class CategoryRefreshEvent{

    private final EventType eventType;

    public CategoryRefreshEvent(EventType eventType) {
        this.eventType = eventType;
    }

    public EventType getEventType() {
        return eventType;
    }


//    public T getPayload() {
//        return payload;
//    }

}
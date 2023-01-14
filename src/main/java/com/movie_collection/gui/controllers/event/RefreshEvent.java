package com.movie_collection.gui.controllers.event;

import com.movie_collection.bll.helpers.EventType;

public class RefreshEvent {

    private final EventType eventType;

    public RefreshEvent(EventType eventType) {
        this.eventType = eventType;
    }

    public EventType getEventType() {
        return eventType;
    }


//    public T getPayload() {
//        return payload;
//    }

}
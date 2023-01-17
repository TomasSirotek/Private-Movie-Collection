package com.movie_collection.gui.controllers.event;

import com.movie_collection.be.Movie;
import com.movie_collection.bll.helpers.EventType;

public class PlayEvent {
    private final EventType eventType;

    private final Movie payLoad;

    public PlayEvent(EventType eventType, Movie payLoad) {
        this.eventType = eventType;
        this.payLoad = payLoad;
    }

    public EventType getEventType() {
        return eventType;
    }


    public Movie getPayload() {
        return payLoad;
    }
}

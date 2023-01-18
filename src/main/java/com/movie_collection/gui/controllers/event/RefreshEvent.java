package com.movie_collection.gui.controllers.event;

import com.movie_collection.bll.helpers.EventType;

public record RefreshEvent(EventType eventType) {
}
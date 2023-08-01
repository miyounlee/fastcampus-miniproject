package com.application.miniproject.event.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventType {

    LEAVE("연차"),
    DUTY("당직");

    private final String type;

}

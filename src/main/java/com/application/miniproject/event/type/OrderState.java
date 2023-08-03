package com.application.miniproject.event.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public enum OrderState {

    WAITING("승인 대기"),
    APPROVED("승인 완료"),
    REJECTED("승인 반려");

    private final String state;
}

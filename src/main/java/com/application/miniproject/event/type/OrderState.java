package com.application.miniproject.event.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderState {

    WAITING("승인 대기"),
    APPROVED("승인 완료"),
    REJECTED("승인 반려"),
    CANCEL("승인 취소");  // user가 취소할 때

    private final String state;
}

package com.application.miniproject.admin.dto;

import com.application.miniproject.event.type.OrderState;
import lombok.Getter;
import lombok.Builder;

public class AdminRequest {

    private AdminRequest() {
        //인스턴스화 방지
    }

    @Getter
    @Builder
    public static class ApprovalDTO {
        private final Long eventId;
        private final OrderState orderState;
    }
}

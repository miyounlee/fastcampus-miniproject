package com.application.miniproject.admin.dto;

import com.application.miniproject.event.type.OrderState;
import lombok.Getter;
import lombok.Setter;

public class AdminRequest {

    @Getter
    @Setter
    public static class UserDetailsDTO {
        private String phoneNumber;
        private String position;
        private String roles;
    }

    @Getter
    @Setter
    public static class ApprovalDTO {
        private Long eventId;
        private OrderState orderState;
    }
}

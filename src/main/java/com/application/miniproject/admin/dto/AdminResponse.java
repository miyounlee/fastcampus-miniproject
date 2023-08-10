package com.application.miniproject.admin.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

public class AdminResponse {

    private AdminResponse() {
        //인스턴스화 방지
    }

    @Getter
    @Builder
    public static class EventRequestListDTO {
        private final Long eventId;
        private final Long userId;
        private final String userName;
        private final String userEmail;
        private final String eventType;
        private final LocalDate startDate;
        private final LocalDate endDate;
        private final String orderState;
        private final int annualCount;
    }

    @Getter
    @Builder
    public static class LeaveApprovalDTO {
        private final Long userId;
        private final String userName;
        private final String userEmail;
        private final String eventType;
        private final Long eventId;
        private final LocalDate startDate;
        private final LocalDate endDate;
        private final String orderState;
    }

    @Getter
    @Builder
    public static class DutyApprovalDTO {
        private final Long userId;
        private final String userName;
        private final String userEmail;
        private final String eventType;
        private final Long eventId;
        private final LocalDate startDate;
        private final LocalDate endDate;
        private final String orderState;
    }
}

package com.application.miniproject.admin.dto;

import com.application.miniproject.event.Event;
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

        public EventRequestListDTO(Event event) {
            this.eventId = event.getId();
            this.userId = event.getUser().getId();
            this.userName = event.getUser().getUsername();
            this.userEmail = event.getUser().getEmail();
            this.eventType = event.getEventType().toString();
            this.startDate = event.getStartDate();
            this.endDate = event.getEndDate();
            this.orderState = event.getOrderState().toString();
        }
    }
}

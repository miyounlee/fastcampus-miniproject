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

        public static EventRequestListDTO fromEvent(Event event) {
            return EventRequestListDTO.builder()
                    .eventId(event.getId())
                    .userId(event.getUser().getId())
                    .userName(event.getUser().getUsername())
                    .userEmail(event.getUser().getEmail())
                    .eventType(event.getEventType().toString())
                    .startDate(event.getStartDate())
                    .endDate(event.getEndDate())
                    .orderState(event.getOrderState().toString())
                    .build();
        }
    }
    @Getter
    @Builder
    public static class ApprovalResultDTO {
        private final Long eventId;
        private final String userName;
        private final String userEmail;
        private final String eventType;
        private final String orderState;
    }
}

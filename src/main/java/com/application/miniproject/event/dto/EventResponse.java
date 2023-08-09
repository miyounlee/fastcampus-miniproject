package com.application.miniproject.event.dto;

import com.application.miniproject._core.security.Aes256;
import com.application.miniproject.event.Event;
import com.application.miniproject.event.type.EventType;
import com.application.miniproject.event.type.OrderState;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;
import java.time.LocalDate;

public class EventResponse {

    @Builder
    @Getter
    public static class AddDTO {

        private Long userId;
        private Long eventId;
        private EventType eventType;
        private LocalDate startDate;
        private LocalDate endDate;
        private Timestamp createdAt;

        public static AddDTO from(Event event) {
            return AddDTO.builder()
                    .userId(event.getUser().getId())
                    .eventId(event.getId())
                    .eventType(event.getEventType())
                    .startDate(event.getStartDate())
                    .endDate(event.getEndDate())
                    .createdAt(event.getCreatedAt())
                    .build();
        }
    }

    @Builder
    @Getter
    public static class ListDTO {

        private String username;
        private int annualCount;
        private Long eventId;
        private EventType eventType;
        private LocalDate startDate;
        private LocalDate endDate;
        private OrderState orderState;

        public static ListDTO from(Event event, Aes256 aes256) {
            return ListDTO.builder()
                    .username(aes256.decrypt(event.getUser().getUsername()))
                    .annualCount(event.getUser().getAnnualCount())
                    .eventId(event.getId())
                    .eventType(event.getEventType())
                    .startDate(event.getStartDate())
                    .endDate(event.getEndDate())
                    .orderState(event.getOrderState())
                    .build();
        }
    }
}

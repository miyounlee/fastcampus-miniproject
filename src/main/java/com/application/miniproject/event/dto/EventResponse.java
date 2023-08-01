package com.application.miniproject.event.dto;

import com.application.miniproject.event.Event;
import com.application.miniproject.event.type.EventType;
import com.application.miniproject.event.type.OrderState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;
import java.time.LocalDate;

public class EventResponse {

    @Getter
    @Builder
    @AllArgsConstructor
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

    @Getter
    @Builder
    public static class ListDTO {

        private Long eventId;
        private Long userId;
        private String username;
        private String userEmail;
        private String UserImageUrl;
        private EventType eventType;
        private LocalDate startDate;
        private LocalDate endDate;
        private Timestamp createdAt;
        private Timestamp updatedAt;
        private OrderState orderState;

        public static ListDTO from(Event event) {
            return ListDTO.builder()
                    .eventId(event.getId())
                    .userId(event.getUser().getId())
                    .username(event.getUser().getUsername())
                    .userEmail(event.getUser().getEmail())
                    .UserImageUrl(event.getUser().getImageUrl())
                    .eventType(event.getEventType())
                    .startDate(event.getStartDate())
                    .endDate(event.getEndDate())
                    .createdAt(event.getCreatedAt())
                    .updatedAt(event.getUpdatedAt())
                    .orderState(event.getOrderState())
                    .build();
        }
    }
}

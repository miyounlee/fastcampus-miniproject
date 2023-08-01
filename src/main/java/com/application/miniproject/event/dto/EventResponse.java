package com.application.miniproject.event.dto;

import com.application.miniproject.event.Event;
import com.application.miniproject.event.type.EventType;
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
}

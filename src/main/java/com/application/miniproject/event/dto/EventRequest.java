package com.application.miniproject.event.dto;

import com.application.miniproject.event.Event;
import com.application.miniproject.event.type.EventType;
import com.application.miniproject.event.type.OrderState;
import com.application.miniproject.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class EventRequest {

    @NoArgsConstructor
    @Getter
    @Setter
    public static class AddDTO {

        @NotNull
        private EventType eventType;

        @NotNull
        private LocalDate startDate;

        private LocalDate endDate;
        private int count;

        public static Event toEntity(EventRequest.AddDTO addReqDTO, User user) {

            return Event.builder()
                    .user(user)
                    .startDate(addReqDTO.getStartDate())
                    .endDate(addReqDTO.getEndDate())
                    .eventType(addReqDTO.getEventType())
                    .count(addReqDTO.getCount())
                    .build();
        }
    }
}

package com.application.miniproject.admin.dto;

import com.application.miniproject.event.Event;
import com.application.miniproject.user.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class AdminResponse {

    private AdminResponse() {
        //인스턴스화 방지
    }

    @Getter
    @Setter
    public static class EventRequestListDTO {
        private Long eventId;
        private Long userId;
        private String userName;
        private String userEmail;
        private String eventType;
        private LocalDate startDate;
        private LocalDate endDate;
        private String orderState;

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

    @Getter
    @Setter
    public static class UsersListDTO {
        private Long userId;
        private String userName;
        private String userEmail;
        private String userPhoneNumber;
        private String userPosition;
        private LocalDate userJoinDate;

        public UsersListDTO(User user) {
            this.userId = user.getId();
            this.userName = user.getUsername();
            this.userEmail = user.getEmail();
            this.userPhoneNumber = user.getPhoneNumber();
            this.userPosition = user.getPosition();
            this.userJoinDate = user.getJoinDate();
        }
    }

    @Getter
    @Setter
    public static class UserDetailsDTO {
        private Long userId;
        private String userName;
        private String userEmail;
        private String userPhoneNumber;
        private String userPosition;
        private LocalDate userJoinDate;

        public UserDetailsDTO(User user) {
            this.userId = user.getId();
            this.userName = user.getUsername();
            this.userEmail = user.getEmail();
            this.userPhoneNumber = user.getPhoneNumber();
            this.userPosition = user.getPosition();
            this.userJoinDate = user.getJoinDate();
        }
    }
}

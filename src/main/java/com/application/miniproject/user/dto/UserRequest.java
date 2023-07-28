package com.application.miniproject.user.dto;

import com.application.miniproject.user.User;
import com.application.miniproject.util.type.UserType;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class UserRequest {

    @Getter
    @Setter
    public static class JoinDTO {
        private String username;
        private String email;
        private String password;

        public User toEntity() {
            return User.builder()
                    .username(username)
                    .email(email)
                    .password(password)
                    .role(UserType.USER)
                    .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                    .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                    .build();
        }
    }

    @Getter
    @Setter
    public static class LoginDTO {
        private String email;
        private String password;
    }
}

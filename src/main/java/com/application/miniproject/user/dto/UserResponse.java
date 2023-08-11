package com.application.miniproject.user.dto;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

public class UserResponse {

    @Getter
    @Builder
    public static class LoginDTO {
        private Long userId;
        private String username;
        private String email;
        private String imageUrl;
        private String accessToken;
    }

    @Getter
    @Builder
    public static class DuplicateEmailDTO {
        private boolean responseType;
    }

    @Getter
    @Builder
    public static class UserDetailDTO{
        private Long userId;
        private String username;
        private String email;
        private String imageUrl;
        private Timestamp updatedAt;
        private int annualCount;
    }

    @Getter
    @Builder
    public static class authUserDTO {
        private Long userId;
        private String accessToken;
    }
}

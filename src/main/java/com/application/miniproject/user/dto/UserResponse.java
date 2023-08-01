package com.application.miniproject.user.dto;

import lombok.Builder;
import lombok.Getter;

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
}

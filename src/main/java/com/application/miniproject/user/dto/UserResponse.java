package com.application.miniproject.user.dto;

import com.application.miniproject._core.util.type.UserType;
import com.application.miniproject.user.User;
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
}

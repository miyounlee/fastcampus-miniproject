package com.application.miniproject.user;

import com.application.miniproject.util.type.UserType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@ToString
@Entity
@Table(name = "user_tb")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String username;

    @Column(nullable = false, length = 50)
    private String password;

    @Column(nullable = false, length = 50)
    private String email;

    private String imageUrl;
    private int annualCount;

    @Enumerated(EnumType.STRING)
    private UserType role;

    @Column(nullable = false, name = "created_at")
    private Timestamp createdAt;

    @Column(nullable = false, name = "updated_at")
    private Timestamp updatedAt;

    @Builder
    public User(Long id, String username, String password, String email, String imageUrl,
                int annualCount, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.imageUrl = imageUrl;
        this.annualCount = annualCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}

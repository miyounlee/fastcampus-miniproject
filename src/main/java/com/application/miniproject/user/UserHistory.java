package com.application.miniproject.user;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Getter
@Entity
@Table(name = "user_history_tb")
public class UserHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, name = "user_agent")
    private String userAgent;

    @Column(nullable = false, name = "client_ip")
    private String clientIp;

    @Column(nullable = false, name = "created_at")
    private Timestamp createdAt;

}

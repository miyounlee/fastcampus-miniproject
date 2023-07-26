package com.application.miniproject.event;

import com.application.miniproject.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;

@Getter
@ToString
@Entity
@Table(name = "event_tb")
@NoArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate startDate;

    private LocalDate endDate;

    private int count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String eventType;

    @Column(nullable = false)
    @ColumnDefault("WAITING")
    private String orderState;

    @Column(nullable = false, name = "created_at")
    private Timestamp createdAt;

    @Builder
    public Event(Long id, LocalDate startDate, LocalDate endDate,
                 int count, User user, String eventType, String orderState, Timestamp createdAt) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.count = count;
        this.user = user;
        this.eventType = eventType;
        this.orderState = orderState;
        this.createdAt = createdAt;
    }
}

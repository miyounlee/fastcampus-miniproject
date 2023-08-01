package com.application.miniproject.event;

import com.application.miniproject.event.type.EventType;
import com.application.miniproject.event.type.OrderState;
import com.application.miniproject.user.User;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@Builder
@Getter
@Entity
@Table(name = "event_tb")
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

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'WAITING'")
    private OrderState orderState;

    @Column(nullable = false, name = "created_at")
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(nullable = false, name = "updated_at")
    @UpdateTimestamp
    private Timestamp updatedAt;

}

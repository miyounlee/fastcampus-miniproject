package com.application.miniproject.event;

import com.application.miniproject.event.type.EventType;
import com.application.miniproject.event.type.OrderState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {

    Optional<Event> findByEventTypeAndStartDateAndOrderState(@Param("eventType") EventType eventType, @Param("startDate") LocalDate startDate, @Param("orderState") OrderState orderState);

    List<Event> findAllByUserId(@Param("id") Long id);

    @Query("SELECT e FROM Event e LEFT JOIN e.user u " +
            "WHERE u.id = :id AND e.orderState != 'REJECTED' " +
            "AND ((:endDate BETWEEN e.startDate AND e.endDate) OR (:startDate BETWEEN e.startDate AND e.endDate))")
    List<Event> findEvent(@Param("id") Long id, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}

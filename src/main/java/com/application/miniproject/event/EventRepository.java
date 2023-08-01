package com.application.miniproject.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("SELECT e FROM Event e WHERE e.eventType = 'DUTY' AND e.startDate = :dutyDate AND e.orderState = 'APPROVED'")
    Optional<Event> findByDutyDate(@Param("dutyDate") LocalDate dutyDate);

    @Query("SELECT e FROM Event e LEFT JOIN e.user u WHERE u.id = :id")
    List<Event> findAllByUserId(@Param("id") Long id);
}

package com.application.miniproject.event;

import com.application.miniproject.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("SELECT e FROM Event e WHERE e.eventType = 'DUTY' AND e.startDate = :dutyDate AND e.orderState = 'APPROVED'")
    Optional<Event> findByDutyDate(@Param("dutyDate") LocalDate dutyDate);

    List<Event> findAllByUserId(@Param("id") Long id);

    @Query("SELECT e FROM Event e WHERE e.user = :user AND " +
            "(:startDate >= e.startDate AND :startDate <= e.endDate) " +
            "OR (:endDate >= e.startDate AND :endDate <= e.endDate) " +
            "OR (:startDate = e.startDate OR :startDate = e.endDate) " +
            "OR (:endDate = e.startDate OR :endDate = e.endDate) " +
            "OR (e.startDate >= :startDate AND e.startDate <= :endDate)" +
            "OR (e.endDate >= :startDate AND e.endDate <= :endDate)")
    List<Event> findDuplicatedEvent(@Param("user") User user, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}

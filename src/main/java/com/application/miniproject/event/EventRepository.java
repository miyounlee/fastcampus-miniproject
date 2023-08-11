package com.application.miniproject.event;

import com.application.miniproject.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("SELECT e FROM Event e WHERE e.eventType = 'DUTY' AND e.startDate = :startDate AND e.orderState = 'APPROVED'")
    Optional<Event> findByDutyDate(@Param("startDate") LocalDate startDate);

    List<Event> findAllByUserId(@Param("id") Long id);

    @Query("SELECT e FROM Event e LEFT JOIN e.user u WHERE u.id = :id AND e.orderState != 'REJECTED' AND " +
            ":startDate BETWEEN e.startDate AND e.endDate")
    Optional<Event> findByMyDutyDate(@Param("id") Long id, @Param("startDate") LocalDate startDate);

    @Query("SELECT e FROM Event e LEFT JOIN e.user u " +
            "WHERE u.id = :id AND e.orderState != 'REJECTED' AND " +
            ":startDate BETWEEN e.startDate AND e.endDate")
    Optional<Event> findByLeaveDate(@Param("id") Long id, @Param("startDate") LocalDate startDate);

    @Query("SELECT e FROM Event e LEFT JOIN e.user u WHERE u.id = :id AND e.orderState != 'REJECTED' AND " +
            "(e.startDate <= :endDate AND e.endDate >= :startDate)")
    List<Event> findByLeaveDates(@Param("id") Long id, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}

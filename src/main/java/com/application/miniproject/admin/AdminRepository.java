package com.application.miniproject.admin;

import com.application.miniproject.event.Event;
import com.application.miniproject.user.User;
import com.application.miniproject.event.type.OrderState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AdminRepository {

    private final EntityManager entityManager;

    public List<Event> findEventsByStatus(OrderState orderState) {
        Query query = entityManager.createQuery(
                "SELECT e FROM Event e WHERE e.orderState = :orderState", Event.class);
        query.setParameter("orderState", orderState);

        return query.getResultList();
    }

    public List<Event> findAllEvents() {
        Query query = entityManager.createQuery(
                "SELECT e FROM Event e", Event.class);

        return query.getResultList();
    }

    @Transactional
    public void updateEventStatusById(Long eventId, OrderState orderState) {
        Query query = entityManager.createQuery(
                "UPDATE Event e SET e.orderState = :orderState WHERE e.id = :id");
        query.setParameter("orderState", orderState);
        query.setParameter("id", eventId);
        query.executeUpdate();
    }

    public Event findEventById(Long id) {
        return entityManager.find(Event.class, id);
    }

    public List<User> findAllUsers() {
        Query query = entityManager.createQuery(
                "SELECT u FROM User u", User.class);

        return query.getResultList();
    }
    public User findUserById(Long userId) {
        return entityManager.find(User.class, userId);
    }

}

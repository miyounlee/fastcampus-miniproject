package com.application.miniproject.admin;

import com.application.miniproject.event.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AdminRepository {

    private final EntityManager entityManager;

    public List<Event> findAllEvents() {
        Query query = entityManager.createQuery(
                "SELECT e FROM Event e", Event.class);

        return query.getResultList();
    }

    public Event findEventById(Long id) {
        return entityManager.find(Event.class, id);
    }


}

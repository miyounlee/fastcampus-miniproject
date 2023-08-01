package com.application.miniproject.event;

import com.application.miniproject.event.dto.EventRequest;
import com.application.miniproject.event.dto.EventResponse;
import com.application.miniproject.event.type.EventType;
import com.application.miniproject.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class EventService {

    private final EventRepository eventRepository;

    @Transactional
    public EventResponse.AddDTO add(EventRequest.AddDTO addReqDTO, User user) {

        if (addReqDTO.getEventType() == EventType.LEAVE && addReqDTO.getCount() > user.getAnnualCount()) {
            throw new RuntimeException("사용가능한 연차가 부족합니다.");
        }

        if (addReqDTO.getEventType() == EventType.DUTY) {
            eventRepository.findByDutyDate(addReqDTO.getStartDate()).ifPresent(event -> {
                throw new RuntimeException("해당 일자에 승인된 당직신청 내역이 존재합니다.");
            });
        }

        Event event = EventRequest.AddDTO.toEntity(addReqDTO, user);
        Event saveEvent = eventRepository.save(event);

        return EventResponse.AddDTO.from(saveEvent);
    }
}

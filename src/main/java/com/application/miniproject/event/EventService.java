package com.application.miniproject.event;

import com.application.miniproject.event.dto.EventRequest;
import com.application.miniproject.event.dto.EventResponse;
import com.application.miniproject.event.type.EventType;
import com.application.miniproject.event.type.OrderState;
import com.application.miniproject.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional
    public void cancel(Long eventId) {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 이벤트로 삭제가 불가 합니다."));

        if (event.getOrderState() != OrderState.WAITING) {
            throw new RuntimeException("이미 승인되어 취소가 불가 합니다.");
        }

        eventRepository.deleteById(eventId);
    }

    @Transactional(readOnly = true)
    public List<EventResponse.ListDTO> myEventList(Long userId) {

        List<Event> eventList = eventRepository.findAllByUserId(userId);

        return eventList.stream()
                .map(EventResponse.ListDTO::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EventResponse.ListDTO> eventList() {

        List<Event> eventList = eventRepository.findAll();

        return eventList.stream()
                .map(EventResponse.ListDTO::from)
                .collect(Collectors.toList());
    }
}

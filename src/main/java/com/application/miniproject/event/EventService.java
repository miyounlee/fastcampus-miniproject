package com.application.miniproject.event;

import com.application.miniproject._core.error.exception.Exception500;
import com.application.miniproject._core.security.Aes256;
import com.application.miniproject.event.dto.EventRequest;
import com.application.miniproject.event.dto.EventResponse;
import com.application.miniproject.event.type.EventType;
import com.application.miniproject.event.type.OrderState;
import com.application.miniproject.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EventService {

    private final EventRepository eventRepository;
    private final Aes256 aes256;

    @Transactional
    public EventResponse.AddDTO add(EventRequest.AddDTO addReqDTO, User user) {

        validateAddEventInfo(addReqDTO, user);

        Event event = EventRequest.AddDTO.toEntity(addReqDTO, user);
        Event saveEvent = eventRepository.save(event);

        return EventResponse.AddDTO.from(saveEvent);
    }

    private void validateAddEventInfo(EventRequest.AddDTO addReqDTO, User user) {

        if (addReqDTO.getEventType() == EventType.LEAVE && addReqDTO.getCount() > user.getAnnualCount()) {
            throw new Exception500("사용가능한 연차가 부족합니다.");
        }

        if (addReqDTO.getEventType() == EventType.DUTY) {
            eventRepository.findByEventTypeAndStartDateAndOrderState(EventType.DUTY, addReqDTO.getStartDate(), OrderState.APPROVED).ifPresent(event -> {
                throw new Exception500("해당 일자에 승인된 당직신청 내역이 존재합니다.");
            });
        }

        if (!eventRepository.findEvent(user.getId(), addReqDTO.getStartDate(), addReqDTO.getEndDate()).isEmpty()) {
            throw new Exception500("해당 일자에 신청내역이 존재합니다. 확인 후 다시 신청해 주세요.");
        }
    }

    @Transactional
    public void cancel(Long eventId, Long userId) {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new Exception500("존재하지 않는 이벤트로 삭제가 불가합니다."));

        if (!Objects.equals(event.getUser().getId(), userId)) {
            throw new Exception500("본인의 연차/당직만 취소가 가능합니다.");
        }

        if (event.getOrderState() != OrderState.WAITING) {
            throw new Exception500("이미 승인되어 취소가 불가합니다.");
        }

        eventRepository.deleteById(eventId);
    }

    @Transactional(readOnly = true)
    public List<EventResponse.ListDTO> myEventList(Long userId) {

        List<Event> eventList = eventRepository.findAllByUserId(userId);

        return eventList.stream()
                .map(event -> EventResponse.ListDTO.from(event, aes256))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EventResponse.ListDTO> eventList() {

        List<Event> eventList = eventRepository.findAll();

        return eventList.stream()
                .map(event -> EventResponse.ListDTO.from(event, aes256))
                .collect(Collectors.toList());
    }

}

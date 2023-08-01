package com.application.miniproject.event;


import com.application.miniproject._core.security.MyUserDetails;
import com.application.miniproject.event.dto.EventRequest;
import com.application.miniproject.event.dto.EventResponse;
import com.application.miniproject.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("/user/event")
@RestController
public class EventController {

    private final EventService eventService;

    @PostMapping("/add")
    public ResponseEntity<?> addEvent(@RequestBody @Valid EventRequest.AddDTO addReqDTO,
                                      @AuthenticationPrincipal MyUserDetails myUserDetails) {

        User user = myUserDetails.getUser();
        EventResponse.AddDTO addRespDTO = eventService.add(addReqDTO, user);

        return ResponseEntity.ok(addRespDTO);
    }

    @PostMapping("/cancel")
    public ResponseEntity<?> cancelEvent(@RequestBody @Valid EventRequest.CancelDTO cancelReqDTO) {

        eventService.cancel(cancelReqDTO.getEventId());

        return ResponseEntity.ok(true);
    }

    @GetMapping("/myList")
    public ResponseEntity<?> myEventList(@AuthenticationPrincipal MyUserDetails myUserDetails) {

        User user = myUserDetails.getUser();
        Long userId = user.getId();

        List<EventResponse.ListDTO> listDTOS = eventService.myEventList(userId);

        return ResponseEntity.ok(listDTOS);
    }

    @GetMapping("/list")
    public ResponseEntity<?> eventList() {

        List<EventResponse.ListDTO> listDTOS = eventService.eventList();

        return ResponseEntity.ok(listDTOS);
    }
}

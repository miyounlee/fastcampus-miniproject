package com.application.miniproject.event;

import com.application.miniproject._core.security.MyUserDetails;
import com.application.miniproject._core.util.ApiUtils;
import com.application.miniproject.event.dto.EventRequest;
import com.application.miniproject.event.dto.EventResponse;
import com.application.miniproject.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class EventController {

    private final EventService eventService;

    @PostMapping("/user/event")
    public ResponseEntity<?> addEvent(@RequestBody @Valid EventRequest.AddDTO addReqDTO,
                                      @AuthenticationPrincipal MyUserDetails myUserDetails) {

        User user = myUserDetails.getUser();
        EventResponse.AddDTO addRespDTO = eventService.add(addReqDTO, user);

        return ResponseEntity.ok(new ApiUtils<>(addRespDTO));
    }

    @DeleteMapping("/user/event/{id}")
    public ResponseEntity<?> cancelEvent(@PathVariable Long id, @AuthenticationPrincipal MyUserDetails myUserDetails) {

        Long userId = myUserDetails.getUser().getId();
        eventService.cancel(id, userId);

        return ResponseEntity.ok(new ApiUtils<>(true));
    }

    @GetMapping("/user/event")
    public ResponseEntity<?> myEventList(@AuthenticationPrincipal MyUserDetails myUserDetails) {

        Long userId = myUserDetails.getUser().getId();

        List<EventResponse.ListDTO> listDTOS = eventService.myEventList(userId);

        return ResponseEntity.ok(new ApiUtils<>(listDTOS));
    }

    @GetMapping("/events")
    public ResponseEntity<?> eventList() {

        List<EventResponse.ListDTO> listDTOS = eventService.eventList();

        return ResponseEntity.ok(new ApiUtils<>(listDTOS));
    }

    @GetMapping("/healthCheck")
    public ResponseEntity<?> healthCheck() {
        return ResponseEntity.ok(new ApiUtils<>());
    }
}

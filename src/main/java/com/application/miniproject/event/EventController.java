package com.application.miniproject.event;

import com.application.miniproject._core.dto.ApiResponse;
import com.application.miniproject._core.security.MyUserDetails;
import com.application.miniproject.event.dto.EventRequest;
import com.application.miniproject.event.dto.EventResponse;
import com.application.miniproject.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

        return ResponseEntity.ok(new ApiResponse<>(addRespDTO));
    }

    @PostMapping("/cancel/{id}")
    public ResponseEntity<?> cancelEvent(@PathVariable Long id) {

        eventService.cancel(id);

        return ResponseEntity.ok(new ApiResponse<>(true));
    }

    @GetMapping("/myList")
    public ResponseEntity<?> myEventList(@AuthenticationPrincipal MyUserDetails myUserDetails) {

        User user = myUserDetails.getUser();
        Long userId = user.getId();

        List<EventResponse.ListDTO> listDTOS = eventService.myEventList(userId);

        return ResponseEntity.ok(new ApiResponse<>(listDTOS));
    }

    @GetMapping("/list")
    public ResponseEntity<?> eventList() {

        List<EventResponse.ListDTO> listDTOS = eventService.eventList();

        return ResponseEntity.ok(new ApiResponse<>(listDTOS));
    }
}

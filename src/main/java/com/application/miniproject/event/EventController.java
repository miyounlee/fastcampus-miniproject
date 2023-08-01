package com.application.miniproject.event;


import com.application.miniproject._core.security.MyUserDetails;
import com.application.miniproject.event.dto.EventRequest;
import com.application.miniproject.event.dto.EventResponse;
import com.application.miniproject.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
}

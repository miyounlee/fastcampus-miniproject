package com.application.miniproject.user;

import com.application.miniproject.user.dto.UserRequest;
import com.application.miniproject.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<?> join(@Valid @RequestBody UserRequest.JoinDTO joinDTO) {
        userService.joinUser(joinDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserRequest.LoginDTO loginDTO) {
        UserResponse.LoginDTO response = userService.loginUser(loginDTO);

        return ResponseEntity.ok().body(response);
    }


    @PostMapping("/email")
    public ResponseEntity<?> email(@Valid @RequestBody UserRequest.EmailDTO emailDTO) {
        UserResponse.DuplicateEmailDTO duplicateEmailDTO = userService.isEmailUser(emailDTO);

        return ResponseEntity.ok().body(duplicateEmailDTO);
    }
}

package com.application.miniproject.user;

import com.application.miniproject._core.security.JwtProvider;
import com.application.miniproject._core.security.MyUserDetails;
import com.application.miniproject.user.dto.UserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtProvider jwtProvider;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody UserRequest.JoinDTO joinDTO) {
        String password = bCryptPasswordEncoder.encode(joinDTO.getPassword());
        joinDTO.setPassword(password);

        userRepository.save(joinDTO.toEntity());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequest.LoginDTO loginDTO) {
        Optional<User> userOP = userRepository.findByEmail(loginDTO.getEmail());

        String jwt = jwtProvider.generateToken(userOP.get());

        return ResponseEntity.ok().header("Authorization", "Bearer " + jwt).build();
    }

    @GetMapping("/test")
    public ResponseEntity<?> test(@AuthenticationPrincipal MyUserDetails myUserDetails) {
        System.out.println(myUserDetails.getUsername());
        return ResponseEntity.ok().body("ok");
    }
}

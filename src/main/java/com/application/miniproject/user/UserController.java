package com.application.miniproject.user;

import com.application.miniproject._core.security.MyUserDetails;
import com.application.miniproject._core.util.ApiUtils;
import com.application.miniproject.user.dto.UserRequest;
import com.application.miniproject.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<?> join(@Valid @RequestBody UserRequest.JoinDTO joinDTO) {
        userService.joinUser(joinDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiUtils.success("회원가입 성공"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserRequest.LoginDTO loginDTO, HttpServletRequest request) {
        UserResponse.LoginDTO response = userService.loginUser(loginDTO, request);

        return ResponseEntity.ok().body(new ApiUtils<>(response));
    }


    @PostMapping("/email")
    public ResponseEntity<?> email(@Valid @RequestBody UserRequest.EmailDTO emailDTO) {
        UserResponse.DuplicateEmailDTO duplicateEmailDTO = userService.isEmailUser(emailDTO);

        return ResponseEntity.ok().body(new ApiUtils<>(duplicateEmailDTO));
    }

    @GetMapping("/myinfo")
    public ResponseEntity<?> getMypage(@AuthenticationPrincipal MyUserDetails myUserDetails)  {
        UserResponse.UserDetailDTO detailOutDTO = userService.userDetail(myUserDetails.getUser().getId());
        ApiUtils<?> responseDTO = new ApiUtils<>(detailOutDTO);

        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("/myinfo")
    public ResponseEntity<?> updateMypage(
            @AuthenticationPrincipal MyUserDetails myUserDetails,
            @Valid @RequestPart UserRequest.ModifyDTO modifyDTO,
            @RequestPart MultipartFile image

    ) throws IOException
    {
        UserResponse.UserDetailDTO detailOutDTO = userService.modifyUser(myUserDetails.getUser().getId(), modifyDTO, image);
        ApiUtils<?> responseDTO = new ApiUtils<>(detailOutDTO);

        return ResponseEntity.ok().body(responseDTO);
    }
}

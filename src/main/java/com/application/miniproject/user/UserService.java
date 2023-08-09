package com.application.miniproject.user;

import com.application.miniproject._core.error.exception.Exception400;
import com.application.miniproject._core.error.exception.Exception404;
import com.application.miniproject._core.security.Aes256;
import com.application.miniproject._core.security.JwtProvider;
import com.application.miniproject._core.util.S3Service;
import com.application.miniproject.user.dto.UserRequest;
import com.application.miniproject.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final UserHistoryRepository userHistoryRepository;
    private final JwtProvider jwtProvider;
    private final Aes256 aes256;
    private final S3Service s3Service;

    @Transactional
    public void joinUser(UserRequest.JoinDTO joinDTO) {
        if (userRepository.findByEmail(aes256.encrypt(joinDTO.getEmail())).isPresent()) {
            throw new Exception404("잘못된 요청입니다.");
        }
        String password = bCryptPasswordEncoder.encode(joinDTO.getPassword());
        userRepository.save(joinDTO.toCipherEntity(password, aes256));
    }

    @Transactional
    public UserResponse.LoginDTO loginUser(UserRequest.LoginDTO loginDTO, HttpServletRequest request) {
        User user = userRepository.findByEmail(aes256.encrypt(loginDTO.getEmail()))
                .orElseThrow(() -> new Exception404("잘못된 요청입니다."));

        boolean passwordMatches = bCryptPasswordEncoder.matches(loginDTO.getPassword(), user.getPassword());

        if (!passwordMatches) {
            throw new Exception404("잘못된 요청입니다.");
        }

        createUserHistory(user, request);

        return UserResponse.LoginDTO.builder()
                .userId(user.getId())
                .username(aes256.decrypt(user.getUsername()))
                .email(aes256.decrypt(user.getEmail()))
                .imageUrl(user.getImageUrl())
                .accessToken(jwtProvider.generateToken(user))
                .build();
    }

    private void createUserHistory(User user, HttpServletRequest request) {
        List<String> headers = List.of("X-Forwarded-For", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR",
                "HTTP_X_FORWARDED", "HTTP_FORWARDED_FOR", "HTTP_FORWARDED", "Proxy-Client-IP", "WL-Proxy-Client-IP",
                "HTTP_VIA", "IPV6_ADR");

        String clientIp = null;
        boolean isClientIp = false;

        for (String header : headers) {
            clientIp = request.getHeader(header);
            if (StringUtils.hasText(clientIp) && !clientIp.equals("unknown")) {
                isClientIp = true;
                break;
            }
        }

        if (!isClientIp) {
            clientIp = request.getRemoteAddr();
        }

        String userAgent = request.getHeader("User-Agent");

        UserHistory userHistory = UserHistory.builder()
                .clientIp(clientIp)
                .userAgent(userAgent)
                .user(user)
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        userHistoryRepository.save(userHistory);
    }

    @Transactional(readOnly = true)
    public UserResponse.DuplicateEmailDTO isEmailUser(UserRequest.EmailDTO emailDTO) {
        boolean isEmailUser = userRepository.findByEmail(aes256.encrypt(emailDTO.getEmail())).isEmpty();
        return UserResponse.DuplicateEmailDTO.builder()
                .responseType(isEmailUser)
                .build();
    }

    @Transactional
    public UserResponse.UserDetailDTO userDetail(Long id) {
        User userPS = userRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("해당 유저를 찾을 수 없습니다")
        );
        return UserResponse.UserDetailDTO.builder()
                .userId(userPS.getId())
                .username(aes256.decrypt(userPS.getUsername()))
                .email(aes256.decrypt(userPS.getEmail()))
                .imageUrl(userPS.getImageUrl())
                .annualCount(userPS.getAnnualCount())
                .updatedAt(userPS.getUpdatedAt())
                .build();
    }

    @Transactional
    public UserResponse.UserDetailDTO modifyUser(
            Long id, UserRequest.ModifyDTO modifyDTO,
            MultipartFile image
    ) throws IOException {
        User userPS = userRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("해당 유저를 찾을 수 없습니다")
        );

        String email = userPS.getEmail();
        String imageUrl = userPS.getImageUrl();
        String newPassword;
        String username = userPS.getUsername();

        // 기존 비밀번호 입력받은 값 <==> DB 비밀번호 비교
        boolean passwordMatchesTonewPassword = bCryptPasswordEncoder.matches(modifyDTO.getCurrentPassword(), userPS.getPassword());
        if (!passwordMatchesTonewPassword) {
            throw new Exception400("입력하신 비밀번호가 다릅니다.");
        }

        // 기존 비밀번호, 수정 페이저 기존 비밀번호 체크
        boolean passwordMatches = bCryptPasswordEncoder.matches(modifyDTO.getNewPassword(), userPS.getPassword());
        if (passwordMatches) {
            throw new Exception400("기존 비밀번호와 다르게 입력해주세요.");
        }

        // 새 비밀번호, 비밀번호 확인 비교 여부
        if (!modifyDTO.getNewPassword().equals(modifyDTO.getNewPasswordCheck())) {
            throw new Exception400("비밀번호가 일치하지 않습니다.");
        }

        // 새로운 비밀번호 값이 null이 아닐경우 로직 완성
        if (modifyDTO.getCurrentPassword().equals(null) ||modifyDTO.getNewPasswordCheck().equals(null) || modifyDTO.getNewPasswordCheck().equals(null)) {
            throw new Exception400("비밀번호를 입력해주세요.");
        }

        newPassword = bCryptPasswordEncoder.encode(modifyDTO.getNewPassword());
        // 프로필 이름 빈칸 여부 확인
        if (!modifyDTO.getUsername().isBlank()) {
            username = modifyDTO.getUsername();
        }

        if (!modifyDTO.getImageUrl().isBlank()) {
            imageUrl = modifyDTO.getImageUrl();
            // TODO : S3 저장
//            imageUrl = s3Service.updateImage(userPS.getImageUrl(), image);

        }

        userPS.update(modifyDTO.toEntity(email, newPassword, username, imageUrl));
        return UserResponse.UserDetailDTO.builder()
                .userId(userPS.getId())
                .username(aes256.decrypt(userPS.getUsername()))
                .email(aes256.decrypt(userPS.getEmail()))
                .imageUrl(userPS.getImageUrl())
                .annualCount(userPS.getAnnualCount())
                .updatedAt(userPS.getUpdatedAt())
                .build();
    }
}
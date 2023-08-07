package com.application.miniproject.user;

import com.application.miniproject._core.security.Aes256;
import com.application.miniproject._core.security.JwtProvider;
//import com.application.miniproject._core.util.S3Service;
import com.application.miniproject.user.dto.UserRequest;
import com.application.miniproject.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;

@RequiredArgsConstructor
@Service
public class UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final UserHistoryRepository userHistoryRepository;
    private final JwtProvider jwtProvider;
    private final Aes256 aes256;
//    private final S3Service s3Service;

    /**
     * 회원가입
     * TODO 이메일 중복 체크 Custom Exception
     */
    @Transactional
    public void joinUser(UserRequest.JoinDTO joinDTO) {
        if (userRepository.findByEmail(aes256.encrypt(joinDTO.getEmail())).isPresent()) {
            throw new RuntimeException("이메일 중복");
        }
        String password = bCryptPasswordEncoder.encode(joinDTO.getPassword());
        userRepository.save(joinDTO.toCipherEntity(password, aes256));
    }

    /**
     *  로그인
     *  TODO 로그인 에러 custom exception
     */
    @Transactional(readOnly = true)
    public UserResponse.LoginDTO loginUser(UserRequest.LoginDTO loginDTO, HttpServletRequest request) {
        User user = userRepository.findByEmail(aes256.encrypt(loginDTO.getEmail()))
                .orElseThrow(() -> new RuntimeException("로그인 에러"));

        boolean passwordMatches = bCryptPasswordEncoder.matches(loginDTO.getPassword(), user.getPassword());

        if (!passwordMatches) {
            throw new RuntimeException("로그인 에러");
        }

        createUserHistory(user, request);

        return UserResponse.LoginDTO.builder()
                .userId(user.getId())
                .username(aes256.decrypt(user.getEmail()))
                .email(aes256.decrypt(user.getUsername()))
                .imageUrl(user.getImageUrl())
                .accessToken(jwtProvider.generateToken(user))
                .build();
    }

    private void createUserHistory(User user, HttpServletRequest request) {
        try {
            String remoteAddr = request.getRemoteAddr();
            InetAddress inetAddr = InetAddress.getByName(remoteAddr);
            byte[] ipv4Bytes = inetAddr.getAddress();
            String ipv4Addr = InetAddress.getByAddress(ipv4Bytes).getHostAddress();

            String userAgent = request.getHeader("User-Agent");

            UserHistory userHistory = UserHistory.builder()
                    .clientIp(ipv4Addr)
                    .userAgent(userAgent)
                    .user(user)
                    .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                    .build();

            userHistoryRepository.save(userHistory);
        } catch (UnknownHostException e) {
            throw new RuntimeException("로그인 히스토리 에러");
        }
    }

    /**
     *  이메일 검사
     */
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
            Long id, UserRequest.ModifyDTO modifyDTO, MultipartFile image
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
            throw new RuntimeException("입력하신 비밀번호가 다릅니다.");
        }

        // 기존 비밀번호, 수정 페이저 기존 비밀번호 체크
        boolean passwordMatches = bCryptPasswordEncoder.matches(modifyDTO.getNewPassword(), userPS.getPassword());
        if (passwordMatches) {
            throw new RuntimeException("기존 비밀번호와 다르게 입력해주세요.");
        }

        // 새 비밀번호, 비밀번호 확인 비교 여부
        if (!modifyDTO.getNewPassword().equals(modifyDTO.getNewPasswordCheck())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        // 새로운 비밀번호 값이 null이 아닐경우 로직 완성
        if (modifyDTO.getCurrentPassword().equals(null) ||modifyDTO.getNewPasswordCheck().equals(null) || modifyDTO.getNewPasswordCheck().equals(null)) {
            throw new RuntimeException("비밀번호를 입력해주세요.");
        }

        newPassword = bCryptPasswordEncoder.encode(modifyDTO.getNewPassword());
        // 프로필 이름 빈칸 여부 확인
        if (!modifyDTO.getUsername().isBlank()) {
            username = modifyDTO.getUsername();
        }

//        if (image == null) {
//            imageUrl = userPS.getImageUrl();
//        } else {
        // TODO : S3 image Url
//            imageUrl = s3Service.updateImage(userPS.getImageUrl(), image);
//        }

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
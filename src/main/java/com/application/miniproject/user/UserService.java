package com.application.miniproject.user;

import com.application.miniproject._core.security.Aes256;
import com.application.miniproject._core.security.JwtProvider;
import com.application.miniproject.user.dto.UserRequest;
import com.application.miniproject.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final Aes256 aes256;

    /**
     * 회원가입
     * TODO 이메일 중복 체크 Custom Exception
     */
    @Transactional
    public void joinUser(UserRequest.JoinDTO joinDTO) {
        if (userRepository.findByEmail(joinDTO.getEmail()).isPresent()) {
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
    public UserResponse.LoginDTO loginUser(UserRequest.LoginDTO loginDTO) {
        User user = userRepository.findByEmail(aes256.encrypt(loginDTO.getEmail()))
                .orElseThrow(() -> new RuntimeException("로그인 에러"));

        boolean passwordMatches = bCryptPasswordEncoder.matches(loginDTO.getPassword(), user.getPassword());

        if (!passwordMatches) {
            throw new RuntimeException("로그인 에러");
        }
        return UserResponse.LoginDTO.builder()
                .userId(user.getId())
                .username(aes256.decrypt(user.getEmail()))
                .email(aes256.decrypt(user.getUsername()))
                .imageUrl(user.getImageUrl())
                .accessToken(jwtProvider.generateToken(user))
                .build();
    }

    /**
     *  이메일 검사
     */
    @Transactional(readOnly = true)
    public UserResponse.DuplicateEmailDTO isEmailUser(UserRequest.EmailDTO emailDTO) {
        boolean isEmailUser = userRepository.findByEmail(emailDTO.getEmail()).isEmpty();
        return UserResponse.DuplicateEmailDTO.builder()
                .responseType(isEmailUser)
                .build();
    }
}
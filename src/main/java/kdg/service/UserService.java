package kdg.service;

import jakarta.servlet.http.HttpServletResponse;
import kdg.config.PasswordEncoder;
import kdg.dto.LoginRequestDto;
import kdg.dto.UserRequestDTO;
import kdg.dto.UserResponseDTO;
import kdg.entity.User;
import kdg.entity.UserRoleEnum;
import kdg.exception.InvalidCredentialsException;
import kdg.jwt.JwtUtil;
import kdg.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";


    // 유저 등록(회원가입) 로직
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO, HttpServletResponse response) {

        String password = passwordEncoder.encode(userRequestDTO.getPassword());

        User user = User.builder()
                .userName(userRequestDTO.getUserName())
                .email(userRequestDTO.getEmail())
                .password(password)
                .role(UserRoleEnum.USER)
                .build();

        // 등록 이메일로 회원 중복 확인
        Optional<User> checkedEmail = userRepository.findByEmail(user.getEmail());
        if (checkedEmail.isPresent()) {
            throw new IllegalArgumentException("이미 중복된 사용자가 존재합니다.");
        }

        // 사용자 Role 확인
//        UserRoleEnum role = UserRoleEnum.USER;
        if (userRequestDTO.isAdmin()) {
            if (!ADMIN_TOKEN.equals(userRequestDTO.getAdminToken())) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            user.updateUserRole(UserRoleEnum.ADMIN);
        }

        User savedUser = userRepository.save(user);

        String token = jwtUtil.createToken(user.getEmail(), user.getRole());
        jwtUtil.addJwtToCookie(token, response);

        return UserResponseDTO.toResponseDTO(savedUser);
    }

    // 유정 단건 조회 로직
    public UserResponseDTO getUser(Long id) {
        User findUser = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 ID를 가진 유저는 존재하지 않습니다."));
        return UserResponseDTO.toResponseDTO(findUser);
    }

    // 유저 목록 조회 로직
    public List<UserResponseDTO> getUsers() {

        return userRepository.findAll().stream()
                .map(UserResponseDTO::toResponseDTO)
                .collect(Collectors.toList());
    }

    // 유저 수정 로직
    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO) {

        // 새로 수정한 비밀번호 암호화
        String password = passwordEncoder.encode(userRequestDTO.getPassword());
        userRequestDTO.setPassword(password);

        return UserResponseDTO.toResponseDTO(userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 ID를 가지는 일정은 존재하지 않습니다.")).updateUser(userRequestDTO));
    }

    // 유저 삭제 로직
    public Long deleteUser(Long id) {

        userRepository.deleteById(id);
        return id;
    }

    // 유저 로그인 로직
    public void login(LoginRequestDto loginRequestDto, HttpServletResponse response) {

        // 유저 확인
        User user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("해당 이메일을 가진 등록된 사용자가 존재하지 않습니다."));

        // 비밀번호 확인
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtUtil.createToken(user.getEmail(), user.getRole());
        jwtUtil.addJwtToCookie(token, response);

    }



}

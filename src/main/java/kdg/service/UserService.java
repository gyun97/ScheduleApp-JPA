package kdg.service;

import kdg.dto.UserRequestDTO;
import kdg.dto.UserResponseDTO;
import kdg.entity.Schedule;
import kdg.entity.User;
import kdg.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 유저 등록 로직
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {

        User user = User.builder()
                .userName(userRequestDTO.getUserName())
                .email(userRequestDTO.getEmail())
                .build();

        User savedUser = userRepository.save(user);
        return UserResponseDTO.toResponseDTO(user);
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

        return UserResponseDTO.toResponseDTO(userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 ID를 가지는 일정은 존재하지 않습니다.")).updateUser(userRequestDTO));
    }

    // 유저 삭제 로직
    public Long deleteUser(Long id) {

        userRepository.deleteById(id);
        return id;
    }

}

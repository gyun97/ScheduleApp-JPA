package kdg.controller;

import jakarta.servlet.http.HttpServletResponse;
import kdg.dto.UserRequestDTO;
import kdg.dto.UserResponseDTO;
import kdg.jwt.JwtUtil;
import kdg.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    // 유저 등록 메서드
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequestDTO, HttpServletResponse response) {
        log.info("유저 추가");
        return ResponseEntity.ok(userService.createUser(userRequestDTO, response));
    }

    // 유저 단건 조회 메서드
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable Long id) {
        log.info("ID가 {}인 유저 조회", id);
        return ResponseEntity.ok(userService.getUser(id));
    }

    // 유저 목록 조회 메서드
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getUsers() {
        log.info("전체 유저 목록 조회");
        return ResponseEntity.ok(userService.getUsers());
    }

    // 유저 수정 메서드
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @RequestBody UserRequestDTO userRequestDTO) {
        log.info("ID가 {}인 유저 정보 수정", id);
        return ResponseEntity.ok(userService.updateUser(id, userRequestDTO));
    }

    // 유저 삭제 메서드
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteUser(@PathVariable Long id) {
        log.info("ID가 {}인 유저 삭제", id);
        userService.deleteUser(id);
        return ResponseEntity.ok(id);
    }
}

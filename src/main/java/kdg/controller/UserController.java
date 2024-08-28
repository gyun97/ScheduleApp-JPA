package kdg.controller;

import jakarta.servlet.http.HttpServletResponse;
import kdg.dto.LoginRequestDto;
import kdg.dto.UserRequestDTO;
import kdg.dto.UserResponseDTO;
import kdg.jwt.JwtUtil;
import kdg.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    // 유저 등록(회원가입) 메서드
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequestDTO, HttpServletResponse response) {
        log.info("회원 가입이 완료되었습니다.");
        UserResponseDTO res = userService.createUser(userRequestDTO, response);
        return ResponseEntity.status(HttpServletResponse.SC_CREATED).body(res);
    }

    // 유저 로그인 메서드
    @PostMapping("/login")
    public ResponseEntity<String> userLogin(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        log.info("로그인 시도");
        userService.login(loginRequestDto, response);
        return ResponseEntity.ok("로그인에 성공하였습니다.");
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
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        log.info("ID가 {}인 유저 삭제", id);
        userService.deleteUser(id);
        return ResponseEntity.ok("유저 삭제 완료");
    }
}

package kdg.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kdg.dto.*;
import kdg.entity.Schedule;
import kdg.entity.UserRoleEnum;
import kdg.jwt.JwtUtil;
import kdg.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final JwtUtil jwtUtil;

    // 일정 추가 메서드
    @PostMapping
    public ResponseEntity<ScheduleResponseDTO> createSchedule(@RequestBody ScheduleRequestDTO scheduleRequestDTO) {
        log.info("일정 추가");
        ScheduleResponseDTO response = scheduleService.save(scheduleRequestDTO);
        return ResponseEntity.ok(response);
    }

    // 일정 조회 메서드(단건)
    @GetMapping("/{id}")
    public ResponseEntity<List<GetScheduleResponseDTO>> getSchedule(@PathVariable Long id) {
        log.info("일정 단건 조회");
        List<GetScheduleResponseDTO> response = scheduleService.getSchedule(id);
        return ResponseEntity.ok(response);
    }

    // 페이지별 일정 조회 메서드(page는 0부터 시작!)
    @GetMapping
    public ResponseEntity<List<GetScheduleListResponseDTO>> getSchedules(@PageableDefault(size = 10, sort = "updatedAt", direction = Sort.Direction.DESC) Pageable pageable) {
        log.info("일정 목록 조회 - 페이지 인덱스: {}, 페이지: {}, 사이즈: {}", pageable.getPageNumber(), pageable.getPageNumber() + 1, pageable.getPageSize());
        List<GetScheduleListResponseDTO> schedules = scheduleService.getSchedules(pageable);
        log.info("{} 페이지에서 조회된 일정 수: {} 개", pageable.getPageNumber() + 1, schedules.size());
        return ResponseEntity.ok(schedules);
    }

    // 관리자 권한 일정 수정
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSchedule(
            @PathVariable Long id,
            @RequestBody ScheduleRequestDTO scheduleRequestDTO,
            HttpServletRequest request
    ) {
        log.info("ID가 {}인 일정의 수정을 시도합니다.", id);
        // 요청에서 JWT 토큰 추출
        String token = jwtUtil.getTokenFromRequest(request);

        // 토큰이 "Bearer "로 시작하는지 확인 후, 시작한다면 접두사를 제거
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);  // "Bearer " 이후의 실제 토큰만 추출
        }

        log.info("현재 유저 권한: {}", jwtUtil.getUserRoleFromToken(token));

        // 권한 확인
        if (!jwtUtil.isAdmin(token)) {
            log.info("관리자만 수정 가능합니다");
            return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).body("권한이 없습니다.");
        }

        ScheduleResponseDTO response = scheduleService.updateSchedule(id, scheduleRequestDTO);
        log.info("ID가 {}인 일정의 수정을 완료하였습니다.", id);
        return ResponseEntity.ok(response);
    }

    // 관리자 일정 삭제 메서드
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteScheduel(@PathVariable Long id, HttpServletRequest request) {
        log.info("ID가 {}인 일정 삭제를 시도합니다.", id);

        String token = jwtUtil.getTokenFromRequest(request);

        // 토큰이 "Bearer "로 시작하는지 확인 후, 시작한다면 접두사를 제거
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);  // "Bearer " 이후의 실제 토큰만 추출
        }
        log.info("현재 유저 권한: {}", jwtUtil.getUserRoleFromToken(token));

        // 권한 확인
        if (!jwtUtil.isAdmin(token)) {
            log.info("관리자만 삭제 가능합니다.");
            return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).body("권한이 없습니다.");
        }

        scheduleService.deleteSchedule(id);
        log.info("ID가 {}인 일정과 해당 일정의 댓글들이 삭제가 완료되었습니다", id);
        return ResponseEntity.ok(id);
    }

    // 일정에 담당 유저 추가 메서드
    @PostMapping("/{id}/assign-users")
    public void addAssignedUser(@PathVariable Long id, @RequestBody AssignedUserDTO assignedUserDTO) {
        log.info("ID {}인 일정에 ID가 {}인 해당 유저들을 추가 배정합니다.", id, assignedUserDTO.getAssignedUserIdList());
        scheduleService.addAssignedUser(id, assignedUserDTO);
    }
}

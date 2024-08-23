package kdg.controller;

import kdg.dto.*;
import kdg.entity.Schedule;
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

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

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

    // 일정 수정 메서드
    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDTO> updateSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDTO scheduleRequestDTO) {
        log.info("ID가 {}인 일정 수정", id);
        ScheduleResponseDTO response = scheduleService.updateSchedule(id, scheduleRequestDTO);
        return ResponseEntity.ok(response);
    }

    // 일정 삭제 메서드
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteScheduel(@PathVariable Long id) {
        log.info("ID가 {}인 일정 삭제", id);
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

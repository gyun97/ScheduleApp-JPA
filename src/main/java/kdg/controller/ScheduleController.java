package kdg.controller;

import kdg.dto.ScheduleRequestDTO;
import kdg.dto.ScheduleResponseDTO;
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
    public ResponseEntity<ScheduleResponseDTO> getSchedule(@PathVariable Long id) {
        log.info("일정 단건 조회");
        ScheduleResponseDTO response = scheduleService.getSchedule(id);
        return ResponseEntity.ok(response);
    }

    // 페이지별 일정 조회 메서드(page는 0부터 시작!)
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDTO>> getSchedules(@PageableDefault(size = 10, sort = "updatedAt", direction = Sort.Direction.DESC) Pageable pageable) {
        log.info("일정 목록 조회 - 페이지: {}, 사이즈: {}", pageable.getPageNumber(), pageable.getPageSize());
        List<ScheduleResponseDTO> schedules = scheduleService.getSchedules(pageable);
        log.info("조회된 일정 수: {}", schedules.size());
        return ResponseEntity.ok(schedules);
    }



    // 일정 수정 메서드
    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDTO> updateSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDTO scheduleRequestDTO) {
        log.info("ID가 {}인 일정 수정", id);
        ScheduleResponseDTO response = scheduleService.updateSchedule(id, scheduleRequestDTO);
        return ResponseEntity.ok(response);
    }

}

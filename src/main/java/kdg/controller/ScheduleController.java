package kdg.controller;

import kdg.dto.ScheduleRequestDTO;
import kdg.dto.ScheduleResponseDTO;
import kdg.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // 일정 수정 메서드
    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDTO> updateSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDTO scheduleRequestDTO) {
        log.info("ID가 {}인 일정 수정", id);
        ScheduleResponseDTO response = scheduleService.updateSchedule(id, scheduleRequestDTO);
        return ResponseEntity.ok(response);
    }

}

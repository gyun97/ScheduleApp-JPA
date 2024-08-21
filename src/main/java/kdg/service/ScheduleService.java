package kdg.service;

import kdg.dto.ScheduleRequestDTO;
import kdg.dto.ScheduleResponseDTO;
import kdg.entity.Schedule;
import kdg.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    // 일정 저장 로직
    public ScheduleResponseDTO save(ScheduleRequestDTO scheduleRequestDTO) {
        Schedule schedule = Schedule.builder()
                .userName(scheduleRequestDTO.getUserName())
                .title(scheduleRequestDTO.getTitle())
                .content(scheduleRequestDTO.getContent())
                .build();

        Schedule savedSchedule = scheduleRepository.save(schedule);
        return ScheduleResponseDTO.toResponseDTO(savedSchedule);
    }

    // 일정 단건 조회 로직
    public ScheduleResponseDTO getSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 ID를 가지는 일정은 존재하지 않습니다."));
        return ScheduleResponseDTO.toResponseDTO(schedule);
    }

    public List<ScheduleResponseDTO> getSchedules(Pageable pageable) {
        Page<Schedule> schedulePage = scheduleRepository.findAll(pageable);

        return schedulePage.getContent().stream()
                .map(ScheduleResponseDTO::toResponseDTO)
                .collect(Collectors.toList());
    }

    // 일정 수정
    public ScheduleResponseDTO updateSchedule(Long id, ScheduleRequestDTO scheduleRequestDTO) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 ID를 가지는 일정은 존재하지 않습니다."));

        // updateSchedule로 더티 체킹
        schedule.updateSchedule(scheduleRequestDTO);
        return ScheduleResponseDTO.toResponseDTO(schedule);

    }


}

package kdg.service;

import kdg.dto.ScheduleRequestDTO;
import kdg.dto.ScheduleResponseDTO;
import kdg.entity.Schedule;
import kdg.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleResponseDTO save(ScheduleRequestDTO scheduleRequestDTO) {
        Schedule schedule = Schedule.builder()
                .userName(scheduleRequestDTO.getUserName())
                .title(scheduleRequestDTO.getTitle())
                .content(scheduleRequestDTO.getContent())
                .build();

        Schedule savedSchedule = scheduleRepository.save(schedule);
        return ScheduleResponseDTO.toResponseDTO(savedSchedule);
    }

    public ScheduleResponseDTO getSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 ID를 가지는 일정은 존재하지 않습니다."));
        return ScheduleResponseDTO.toResponseDTO(schedule);
    }

    public ScheduleResponseDTO updateSchedule(Long id, ScheduleRequestDTO scheduleRequestDTO) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 ID를 가지는 일정은 존재하지 않습니다."));

        // updateSchedule로 더티 체킹
        schedule.updateSchedule(scheduleRequestDTO);
        return ScheduleResponseDTO.toResponseDTO(schedule);

    }


}

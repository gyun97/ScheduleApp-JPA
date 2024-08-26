package kdg.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kdg.dto.*;
import kdg.entity.Schedule;
import kdg.entity.User;
import kdg.entity.UserSchedule;
import kdg.repository.ScheduleRepository;
import kdg.repository.UserRepository;
import kdg.repository.UserScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private final UserScheduleRepository userScheduleRepository;
    private final UserRepository userRepository;
    private final WeatherService weatherService;

    // 일정 저장 로직
    public ScheduleResponseDTO save(ScheduleRequestDTO scheduleRequestDTO) {

        User user = userRepository.findById(scheduleRequestDTO.getUserID()).orElseThrow();

        Schedule schedule = Schedule.builder()
                .userId(scheduleRequestDTO.getUserID())
                .userName(user.getUserName())
                .userEmail(user.getEmail())
                .title(scheduleRequestDTO.getTitle())
                .content(scheduleRequestDTO.getContent())
                .userEmail(user.getEmail())
                .build();

        UserSchedule userSchedule = UserSchedule.builder()
                .schedule(schedule)
                .user(user)
                .build();

        // 날씨 데이터 가져오기
        List<WeatherData> weatherDataList = weatherService.getWeatherData();

        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
        String dateOfSchedule = currentDateTime.format(formatter);

        log.info("오늘 날짜: {}", dateOfSchedule); // ex) 오늘 날짜: 08-26

        // 해당 날짜의 날씨 데이터 추출
        WeatherData weatherData = weatherDataList.stream()
                .filter(data -> String.valueOf(data).contains(dateOfSchedule))
                .findFirst()
                .orElse(null);

        schedule.setWeatherOfDay(weatherData); //날씨 삽입

        user.addUserSchedule(userSchedule);
        schedule.addUserSchedule(userSchedule);

        userScheduleRepository.save(userSchedule);
        return ScheduleResponseDTO.toResponseDTO(scheduleRepository.save(schedule));
    }

    // 일정 단건 조회 로직
    public List<GetScheduleResponseDTO> getSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 ID를 가지는 일정은 존재하지 않습니다."));

        List<GetScheduleResponseDTO> responseDTOList = new ArrayList<>();

        for (UserSchedule userSchedule : schedule.getUserSchedules()) {
            responseDTOList.add(GetScheduleResponseDTO.toGetScheduleResponse(userSchedule));
        }
        return responseDTOList;

    }

    // 일정 페이지 조회 로직
    public List<GetScheduleListResponseDTO> getSchedules(Pageable pageable) {
        Page<Schedule> schedulePage = scheduleRepository.findAll(pageable);

        return schedulePage.getContent().stream()
                .map(GetScheduleListResponseDTO::toGetScheduleListResponseDTO)
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

    // 일정 삭제 로직
    public Long deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
        return id;
    }

    // 일정 담당 유저 추가 로직
    public void addAssignedUser(Long id, AssignedUserDTO assignedUserDTO) {
        for (Long userId : assignedUserDTO.getAssignedUserIdList()) {
            User user = userRepository.findById(userId).orElseThrow();
            Schedule schedule = scheduleRepository.findById(id).orElseThrow();

            UserSchedule userSchedule = UserSchedule.builder()
                    .schedule(schedule)
                    .user(user)
                    .build();

            user.addUserSchedule(userSchedule);
            schedule.addUserSchedule(userSchedule);

            userScheduleRepository.save(userSchedule);
        }
    }
}

package kdg.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kdg.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 일정 등록, 수정할 때의 ResponseDTO
 */
@Getter
@AllArgsConstructor
public class ScheduleResponseDTO {

    private Long id;
    private Long userId;
    private String userName;
    private String title;
    private String content;
    private String UserEmail;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public static ScheduleResponseDTO toResponseDTO(Schedule entity) {
        return new ScheduleResponseDTO(
                entity.getId(),
                entity.getUserId(),
                entity.getUserName(),
                entity.getTitle(),
                entity.getContent(),
                entity.getUserEmail(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

}

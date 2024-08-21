package kdg.dto;

import kdg.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ScheduleResponseDTO {

    private Long id;
//    private Long userId;
    private String userName;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ScheduleResponseDTO toResponseDTO(Schedule entity) {
        return new ScheduleResponseDTO(
                entity.getId(),
//                entity.getUserId(),
                entity.getUserName(),
                entity.getTitle(),
                entity.getContent(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

}

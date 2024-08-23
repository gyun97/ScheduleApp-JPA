package kdg.dto;

import kdg.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;

/**
 * 일정들 (페이지별로) 전체 조회할 때 담당 유저 정보를 제외한 ResponseDTO
 */

@Getter
@AllArgsConstructor
public class GetScheduleListResponseDTO {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static GetScheduleListResponseDTO toGetScheduleListResponseDTO(Schedule entity) {
        return new GetScheduleListResponseDTO(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}

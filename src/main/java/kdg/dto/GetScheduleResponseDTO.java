package kdg.dto;

import kdg.entity.UserSchedule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;

/**
 * 일정 단건 조회할 때의 담당 유저들의 정보가 포함된 ResponseDTO
 */

@Getter
@AllArgsConstructor
public class GetScheduleResponseDTO {

    private Long id;
    private Long userId;
    private String userNames;
    private String userEmails;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static GetScheduleResponseDTO toGetScheduleResponse(UserSchedule userSchedule) {
        return new GetScheduleResponseDTO(
                userSchedule.getSchedule().getId(),
                userSchedule.getUser().getId(),
                userSchedule.getUser().getUserName(),
                userSchedule.getUser().getEmail(),
                userSchedule.getSchedule().getTitle(),
                userSchedule.getSchedule().getContent(),
                userSchedule.getSchedule().getCreatedAt(),
                userSchedule.getSchedule().getUpdatedAt()
        );
    }
}

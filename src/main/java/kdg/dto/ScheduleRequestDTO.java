package kdg.dto;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleRequestDTO {


    private String userName;
    private String content;
    private String title;


}

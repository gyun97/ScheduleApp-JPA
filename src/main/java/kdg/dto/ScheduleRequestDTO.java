package kdg.dto;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleRequestDTO {


    private String userName;
    private String title;
    private String content;


}

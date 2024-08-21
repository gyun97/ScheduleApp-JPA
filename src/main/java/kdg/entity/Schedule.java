package kdg.entity;

import jakarta.persistence.*;
import kdg.dto.ScheduleRequestDTO;
import kdg.dto.ScheduleResponseDTO;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "schedule")
public class Schedule extends TimeStamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


//    private Long userId;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Builder
    public Schedule(String userName, String title, String content) {
        this.userName = userName;
        this.title = title;
        this.content = content;
    }

    public void updateSchedule(ScheduleRequestDTO scheduleRequestDTO) {
        this.userName = scheduleRequestDTO.getUserName();
        this.title = scheduleRequestDTO.getTitle();
        this.content = scheduleRequestDTO.getContent();
        this.updatedAt = LocalDateTime.now();
    }


}

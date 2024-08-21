package kdg.entity;

import jakarta.persistence.*;
import kdg.dto.ScheduleRequestDTO;
import kdg.dto.ScheduleResponseDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "schedule")
public class Schedule extends TimeStamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long id;
//    private Long userId;

    private String userName;
    private String title;
    private String content;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Schedule(Long id, String userName, String title, String content, LocalDateTime updatedAt) {
        this.id = id;
        this.userName = userName;
        this.title = title;
        this.content = content;
        this.updatedAt = LocalDateTime.now();
    }

    // 일정 수정 메서드
    public void updateSchedule(ScheduleRequestDTO scheduleRequestDTO) {
        this.userName = scheduleRequestDTO.getUserName();
        this.title = scheduleRequestDTO.getTitle();
        this.content = scheduleRequestDTO.getContent();
        this.updatedAt = LocalDateTime.now();
    }


}

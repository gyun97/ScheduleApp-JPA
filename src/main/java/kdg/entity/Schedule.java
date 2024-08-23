package kdg.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import kdg.dto.ScheduleRequestDTO;
import lombok.*;
import org.springframework.stereotype.Service;

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
    private Long userId;
    private String userName;
    private String title;
    private String content;
    private String userEmail;

    @OneToMany(mappedBy = "schedule")
    @JsonIgnore
    private List<UserSchedule> userSchedules = new ArrayList<>();

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Schedule(Long id, String userName, String title, String content, LocalDateTime updatedAt, Long userId, String userEmail) {
        this.userId = userId;
        this.id = id;
        this.userName = userName;
        this.title = title;
        this.content = content;
        this.updatedAt = LocalDateTime.now();
        this.userEmail = userEmail;
    }


    // 유저-일정 테이블과의 연관관계 메서드
    public void addUserSchedule(UserSchedule userSchedule) {
        userSchedules.add(userSchedule);
        userSchedule.setSchedule(this);
    }

    // 일정 수정 메서드
    public void updateSchedule(ScheduleRequestDTO scheduleRequestDTO) {
        this.userName = scheduleRequestDTO.getUserName();
        this.title = scheduleRequestDTO.getTitle();
        this.content = scheduleRequestDTO.getContent();
        this.updatedAt = LocalDateTime.now();
    }


}

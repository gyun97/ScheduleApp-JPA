package kdg.entity;

import jakarta.persistence.*;
import kdg.dto.CommentRequestDTO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends TimeStamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;
    private String content;
    private String userName;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule; // 일정

    @Builder
    public Comment(String content, String userName, LocalDateTime updatedAt, Schedule schedule) {
        this.content = content;
        this.userName = userName;
        this.updatedAt = LocalDateTime.now();
        this.schedule = schedule;
    }

    // 일정(Schedule)과의 연관 관계 메서드
    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
        schedule.getComments().add(this);
    }

    // 댓글 수정 메서드
    public void updateComment(CommentRequestDTO commentRequestDTO) {
        this.userName = commentRequestDTO.getUserName();
        this.content = commentRequestDTO.getContent();
        this.updatedAt = LocalDateTime.now();
    }


}

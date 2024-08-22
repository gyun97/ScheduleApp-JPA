package kdg.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import kdg.dto.UserRequestDTO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
public class User extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String userName;
    private String email;


    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<UserSchedule> userSchedules = new ArrayList<>();


    @Builder
    public User(Long id, String userName, String email, LocalDateTime updatedAt) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.updatedAt = LocalDateTime.now();
    }

    // 유저-일정 테이블과의 연관관계 메서드
    public void addUserSchedule(UserSchedule userSchedule) {
        userSchedules.add(userSchedule);
        userSchedule.setUser(this);
    }

    // 유저 수정 메서드
    public User updateUser(UserRequestDTO userRequestDTO) {
        this.userName = userRequestDTO.getUserName();
        this.email = userRequestDTO.getEmail();
        this.updatedAt = LocalDateTime.now();
        return this;
    }

}

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

    @Column(nullable = false, updatable = true, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private UserRoleEnum role;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<UserSchedule> userSchedules = new ArrayList<>();


    @Builder
    public User(Long id, String userName, String email, String password,  UserRoleEnum role, LocalDateTime updatedAt) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.role = role;
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
        this.password = userRequestDTO.getPassword();
        this.updatedAt = LocalDateTime.now();
        return this;
    }

    // 유저 권한 수정
    public void updateUserRole(UserRoleEnum userRole) {
        this.role = userRole;
    }

}

package kdg.dto;

import kdg.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserResponseDTO {

    private Long id;
    private String userName;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static UserResponseDTO toResponseDTO(User entity) {
        return new UserResponseDTO(
                entity.getId(),
                entity.getUserName(),
                entity.getEmail(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}

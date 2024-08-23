package kdg.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 이미 등록된 일정에 담당 유저들을 추가로 배치할 때의 RequestDTO
 */

@Getter
@RequiredArgsConstructor
public class AssignedUserDTO {

    private final List<Long> assignedUserIdList = new ArrayList<>(); // 해당 일정에 추가 배치할 유저들 ID 목록

}

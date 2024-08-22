package kdg.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class AssignedUserDTO {

    private final List<Long> assignedUserIdList = new ArrayList<>();

}

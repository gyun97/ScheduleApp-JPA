package kdg.service;


import kdg.dto.CommentRequestDTO;
import kdg.dto.CommentResponseDTO;
import kdg.dto.ScheduleResponseDTO;
import kdg.entity.Comment;
import kdg.entity.Schedule;
import kdg.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleService scheduleService;

    public CommentResponseDTO createComment(CommentRequestDTO commentRequestDTO) {

        ScheduleResponseDTO findedSchedule = scheduleService.getSchedule(commentRequestDTO.getScheduleId());

        Schedule schedule = Schedule.builder()
                .id(findedSchedule.getId())
                .userName(findedSchedule.getUserName())
                .title(findedSchedule.getTitle())
                .content(findedSchedule.getContent())
                .build();

        Comment comment = Comment.builder()
                .userName(commentRequestDTO.getUserName())
                .content(commentRequestDTO.getContent())
                .schedule(schedule)
                .build();

        comment.setSchedule(schedule);

        Comment savedComment = commentRepository.save(comment);
        return CommentResponseDTO.toResponseDTO(comment);

    }


    public CommentResponseDTO getComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 ID를 가지는 댓글은 존재하지 않습니다."));
        return CommentResponseDTO.toResponseDTO(comment);
    }


    public List<CommentResponseDTO> getAllComments() {
        List<Comment> allComments = commentRepository.findAll();
        return allComments.stream().map(CommentResponseDTO::toResponseDTO).collect(Collectors.toList());
    }

    public CommentResponseDTO updateComment(Long id, CommentRequestDTO commentRequestDTO) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 ID를 가지는 일정은 존재하지 않습니다."));

        // 더티 체킹으로 댓글 내용 수정
        comment.updateComment(commentRequestDTO);

        return CommentResponseDTO.toResponseDTO(comment);
    }

    public Long deleteComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 ID를 가지는 일정은 존재하지 않습니다."));
        commentRepository.delete(comment);
        return comment.getId();

    }
}

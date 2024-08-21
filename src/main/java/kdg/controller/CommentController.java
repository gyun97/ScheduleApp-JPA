package kdg.controller;

import kdg.dto.CommentRequestDTO;
import kdg.dto.CommentResponseDTO;
import kdg.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;


    // 댓글 작성 메서드
    @PostMapping
    public ResponseEntity<CommentResponseDTO> createComment(@RequestBody CommentRequestDTO commentRequestDTO) {
        log.info("댓글 작성");
        CommentResponseDTO response = commentService.createComment(commentRequestDTO);
        return ResponseEntity.ok(response);
    }

    // 댓글 단건 조회 메서드
    @GetMapping("/{id}")
    public ResponseEntity<CommentResponseDTO> getComment(@PathVariable Long id) {
        log.info("ID가 {}인 댓글 단건 조회", id);
        CommentResponseDTO response = commentService.getComment(id);
        return ResponseEntity.ok(response);
    }

    // 댓글 전체 조회 메서드
    @GetMapping
    public ResponseEntity<List<CommentResponseDTO>> getAllComments() {
        log.info("댓글 전체 조회");
        List<CommentResponseDTO> response = commentService.getAllComments();
        return ResponseEntity.ok(response);
    }

    // 댓글 수정 메서드
    @PutMapping("/{id}")
    public ResponseEntity<CommentResponseDTO> updateComment(@PathVariable Long id, @RequestBody CommentRequestDTO commentRequestDTO) {
        log.info("ID가 {}인 댓글 수정", id);
        CommentResponseDTO response = commentService.updateComment(id, commentRequestDTO);
        return ResponseEntity.ok(response);
    }

    // 댓글 삭제 메서드
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteComment(@PathVariable Long id) {
        log.info("ID가 {}인 댓글 삭제", id);
        commentService.deleteComment(id);
        log.info("ID가 {}인 댓글의 삭제가 완료되었습니다", id);
        return ResponseEntity.ok(id);
    }
}

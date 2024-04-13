package exercise.controller;

import exercise.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;

import java.util.List;

import exercise.model.Comment;
import exercise.repository.CommentRepository;
import exercise.exception.ResourceNotFoundException;

// BEGIN
@RestController
@RequestMapping("/comments")
public class CommentsController {
    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("")
    public List<Comment> index() {
        return commentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Comment show(@PathVariable Long id) {
        return commentRepository.findById(id).get();
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Comment create(@RequestBody Comment post) {
        return commentRepository.save(post);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> update(@PathVariable Long id, @RequestBody Comment data) {
        var maybeComment = commentRepository.findById(id);
        if (maybeComment.isPresent()) {
            var comment = maybeComment.get();
            comment.setId(data.getId());
            comment.setBody(data.getBody());
            Comment createdComment = commentRepository.save(comment);
            return ResponseEntity.ok().body(createdComment);
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public void destroy(@PathVariable Long id) {
        var comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Comment by id %s not exist".formatted(id)));
        commentRepository.deleteById(comment.getId());
    }
}
// END

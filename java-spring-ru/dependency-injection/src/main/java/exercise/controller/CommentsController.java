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
import java.util.Optional;

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
    public ResponseEntity<Comment> show(@PathVariable Long id) {

        var maybeComment = commentRepository.findById(id);
        if (maybeComment.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(maybeComment.get());
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
            comment.setBody(data.getBody());
            comment.setPostId(data.getPostId());
            commentRepository.save(comment);
            return ResponseEntity.ok().body(comment);
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public void destroy(@PathVariable Long id) {
        var comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment by id %s not exist".formatted(id)));
        commentRepository.deleteById(comment.getId());
    }
}
// END

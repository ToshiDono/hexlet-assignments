package exercise.controller;

import exercise.repository.CommentRepository;
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

import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.exception.ResourceNotFoundException;

// BEGIN
@RequestMapping("/posts")
@RestController
public class PostsController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("")
    public List<Post> index() {
        return postRepository.findAll();
    }

    @GetMapping("/{id}")
    public Post show(@PathVariable Long id) {
        return postRepository.findById(id).get();
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Post create(@RequestBody Post post) {
        return postRepository.save(post);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> update(@PathVariable Long id, @RequestBody Post data) {
        var maybePost = postRepository.findById(id);
        if (maybePost.isPresent()) {
            var post = maybePost.get();
            post.setId(data.getId());
            post.setTitle(data.getTitle());
            post.setBody(data.getBody());
            Post createdPost = postRepository.save(post);
            return ResponseEntity.ok().body(createdPost);
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public void destroy(@PathVariable Long id) {
        var post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post by id %s not exist".formatted(id)));

        commentRepository.deleteByPostId(id);
        postRepository.deleteById(post.getId());
    }
}
// END

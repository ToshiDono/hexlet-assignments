package exercise.controller.users;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

import exercise.model.Post;
import exercise.Data;

// BEGIN
@RestController
@RequestMapping("/api/users")
public class PostsController {

    private List<Post> posts = Data.getPosts();

    @GetMapping("/{id}/posts") // Список страниц
    public ResponseEntity<List<Post>> index(@PathVariable Integer userId) {
        var result = posts.stream()
                .filter(p -> p.getUserId() == userId).collect(Collectors.toList());
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/{id}/posts") // Создание страницы
    public ResponseEntity<Post> create(@PathVariable Integer userId, @RequestBody Post post) {
        Post newPost = new Post();
        newPost.setUserId(userId);
        newPost.setTitle(post.getTitle());
        newPost.setBody(post.getBody());
        newPost.setSlug(post.getSlug());
        posts.add(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    @GetMapping("/posts") // Список страниц
    public ResponseEntity<List<Post>> index() {
        var result = posts.stream().toList();
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(posts.size()))
                .body(result);
    }
}
// END

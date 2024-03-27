package exercise.controller.users;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public ResponseEntity<List<Post>> index(@PathVariable Integer id) {
        var result = posts.stream()
                .filter(p -> p.getUserId() == id).collect(Collectors.toList());
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/{id}/posts") // Создание страницы
    public ResponseEntity<Post> create(@PathVariable Integer id, @RequestBody Post post) {
        post.setUserId(id);
        posts.add(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }
}
// END

package exercise.controller;

import exercise.daytime.Daytime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

// BEGIN
@RestController
@RequestMapping("/welcome")
public class WelcomeController {
    @Autowired
    private Daytime daytime;

    @GetMapping("")
    public String index() {
        return "It is %s now! Welcome to Spring!".formatted(daytime.getName());
    }
}
// END

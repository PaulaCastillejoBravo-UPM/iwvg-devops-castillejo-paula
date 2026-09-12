package es.upm.miw.devops.es.upm.api;

import es.upm.miw.devops.es.upm.api.infrastructure.data.models.User;
import es.upm.miw.devops.es.upm.api.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{id}")
    public User findById(@PathVariable UUID id) {
        return userService.findById(id);
    }
}

package es.upm.miw.devops.es.upm.api;

import es.upm.miw.devops.es.upm.api.infrastructure.data.models.User;
import es.upm.miw.devops.es.upm.api.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{id}")
    public User findById(@PathVariable UUID id) {
        return userService.findById(id);
    }

    @GetMapping("/user")
    public List<User> findByBillable(
            @RequestParam(required = false) Boolean billable
    ) {
        return userService.findByBillable(billable);
    }

    @DeleteMapping("/user/{id}")
    public void deleteById(@PathVariable UUID id) {
        userService.deleteById(id);
    }

    @PutMapping("/user/{id}/active")
    public User updateActive(
            @PathVariable UUID id,
            @RequestBody Boolean active
    ) {
        return userService.updateActive(id, active);
    }

    @PutMapping("/user/{id}")
    public User updateById(
            @PathVariable UUID id,
            @RequestBody User user
    ) {
        return userService.updateById(id, user);
    }
}

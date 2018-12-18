package com.tolstolutskyi.resource;

import com.tolstolutskyi.model.User;
import com.tolstolutskyi.repository.UserRepository;
import com.tolstolutskyi.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/users")
public class UserResource {
    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    public ResponseEntity register(@RequestBody @Valid User user) {
        userService.register(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<User> getUser(Principal principal) {
        return ResponseEntity.ok(userService.findById(Long.valueOf(principal.getName())));
    }

    @PutMapping
    public ResponseEntity save(@RequestBody @Valid User user, Principal principal) {
        user.setId(Long.valueOf(principal.getName()));
        userService.save(user);
        return ResponseEntity.ok().build();
    }
}

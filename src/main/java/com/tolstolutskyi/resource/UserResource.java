package com.tolstolutskyi.resource;

import com.tolstolutskyi.model.User;
import com.tolstolutskyi.resource.validator.EmailUniquenessValidator;
import com.tolstolutskyi.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
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
    private final EmailUniquenessValidator emailUniquenessValidator;

    public UserResource(UserService userService, EmailUniquenessValidator emailUniquenessValidator) {
        this.userService = userService;
        this.emailUniquenessValidator = emailUniquenessValidator;
    }

    @PostMapping("/registration")
    public ResponseEntity register(@RequestBody @Valid User user, BindingResult bindingResult) {
        emailUniquenessValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
        }
        userService.register(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<User> getUser(Authentication authentication) {
        return ResponseEntity.ok(userService.findById(Long.valueOf(authentication.getName())));
    }

    @PutMapping
    public ResponseEntity save(@RequestBody @Valid User user, Principal principal, BindingResult bindingResult) {
        user.setId(Long.valueOf(principal.getName()));
        emailUniquenessValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
        }
        userService.save(user);
        return ResponseEntity.ok().build();
    }
}

package com.tolstolutskyi.resource;

import com.tolstolutskyi.model.User;
import com.tolstolutskyi.model.projection.UserProjection;
import com.tolstolutskyi.repository.UserRepository;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/users")
public class UserResource {
    private final UserRepository userRepository;
    private final ProjectionFactory projectionFactory;

    public UserResource(UserRepository userRepository, ProjectionFactory projectionFactory) {
        this.userRepository = userRepository;
        this.projectionFactory = projectionFactory;
    }

    @GetMapping("/me")
    public ResponseEntity getById(Principal principal) {
        User user = userRepository.findById(Long.valueOf(principal.getName())).orElseThrow(RuntimeException::new);
        return ResponseEntity.ok(projectionFactory.createProjection(UserProjection.class, user));
    }
}

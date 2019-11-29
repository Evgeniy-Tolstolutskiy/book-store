package com.tolstolutskyi.resource.validator;

import com.tolstolutskyi.model.User;
import com.tolstolutskyi.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class EmailUniquenessValidator implements Validator {
    private final UserService userService;

    public EmailUniquenessValidator(UserService userService) {
        this.userService = userService;
    }

    @Override public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override public void validate(Object o, Errors errors) {
        User user = (User) o;
        if (userService.findByEmail(user.getEmail()) != null) {
            errors.reject("Email is already exist");
        }
    }
}

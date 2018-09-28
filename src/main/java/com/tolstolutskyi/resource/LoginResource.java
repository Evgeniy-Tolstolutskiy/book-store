package com.tolstolutskyi.resource;

import com.tolstolutskyi.dto.LoginData;
import com.tolstolutskyi.service.Oauth2AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/login")
public class LoginResource {
    private final Oauth2AuthenticationService oauth2AuthenticationService;

    public LoginResource(Oauth2AuthenticationService oauth2AuthenticationService) {
        this.oauth2AuthenticationService = oauth2AuthenticationService;
    }

    @PostMapping("/email")
    public ResponseEntity loginByEmail(@RequestBody @Valid LoginData loginData) {
        return ResponseEntity
            .ok(oauth2AuthenticationService.loginViaEmail(loginData.getEmail(), loginData.getPassword()));
    }

    @PostMapping("/refresh")
    public ResponseEntity refreshToken(@RequestParam String refreshToken) throws HttpRequestMethodNotSupportedException {
        return ResponseEntity.ok(oauth2AuthenticationService.getRefreshToken(refreshToken));
    }
}

package com.tolstolutskyi.service;

import com.tolstolutskyi.config.SecurityConfigBean;
import com.tolstolutskyi.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerEndpointsConfiguration;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@Service
public class Oauth2AuthenticationService {
    private final AuthorizationServerEndpointsConfiguration configuration;
    private final TokenEndpoint tokenEndpoint;
    private final SecurityConfigBean securityConfigBean;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public Oauth2AuthenticationService(
        AuthorizationServerEndpointsConfiguration configuration,
        TokenEndpoint tokenEndpoint,
        SecurityConfigBean securityConfigBean,
        PasswordEncoder passwordEncoder,
        UserService userService) {
        this.configuration = configuration;
        this.tokenEndpoint = tokenEndpoint;
        this.securityConfigBean = securityConfigBean;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    public OAuth2AccessToken loginViaEmail(String email, String password) {
        User user = userService.findByEmail(email);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return createOauth2Token(user);
        }
        throw new RuntimeException("username or password is wrong");
    }

    public OAuth2AccessToken getRefreshToken(String refreshToken)
        throws HttpRequestMethodNotSupportedException {
        Principal principal = securityConfigBean::getDefaultClient;
        Authentication authentication = new UsernamePasswordAuthenticationToken(
            principal, null, new ArrayList<>());
        Map<String, String> parameters = new HashMap<>();
        parameters.put("grant_type", "refresh_token");
        parameters.put("refresh_token", refreshToken);
        ResponseEntity<OAuth2AccessToken> oAuth2AccessTokenResponseEntity = tokenEndpoint
            .postAccessToken(authentication, parameters);
        return oAuth2AccessTokenResponseEntity.getBody();
    }

    public OAuth2AccessToken createOauth2Token(User user) {
        OAuth2Request oauth2Request = new OAuth2Request(new HashMap<>(), securityConfigBean.getDefaultClient(),
            user.getAuthorities(), true, new HashSet<>(), new HashSet<>(), null, new HashSet<>(),
            new HashMap<>());

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            user, null, new ArrayList<>());

        OAuth2Authentication auth = new OAuth2Authentication(oauth2Request, authenticationToken);

        AuthorizationServerTokenServices tokenService = configuration.getEndpointsConfigurer().getTokenServices();

        return tokenService.createAccessToken(auth);
    }
}

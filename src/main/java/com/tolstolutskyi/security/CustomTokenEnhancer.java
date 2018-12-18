package com.tolstolutskyi.security;

import com.tolstolutskyi.model.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.HashMap;
import java.util.Map;

public class CustomTokenEnhancer extends JwtAccessTokenConverter {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication auth) {
        if (auth.getPrincipal() instanceof User) {
            User user = (User) auth.getPrincipal();
            final Map<String, Object> additionalInfo = new HashMap<>();
            additionalInfo.put("username", user.getName());

            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
            return super.enhance(accessToken, auth);
        }
        return super.enhance(accessToken, auth);
    }

}

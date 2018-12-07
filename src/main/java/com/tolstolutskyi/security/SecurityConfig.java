package com.tolstolutskyi.security;

import com.tolstolutskyi.config.SecurityConfigBean;
import com.tolstolutskyi.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import static com.tolstolutskyi.common.Constants.UrlRestrictions.EVERYBODY_ALLOWED_URLS;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final SecurityConfigBean securityConfigBean;

    public SecurityConfig(SecurityConfigBean securityConfigBean) {
        this.securityConfigBean = securityConfigBean;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
        tokenConverter.setSigningKey(securityConfigBean.getJwtSignature());
        return tokenConverter;
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setTokenEnhancer(accessTokenConverter());
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }

    @Configuration
    @EnableAuthorizationServer
    public class AuthServerOAuth2Config extends AuthorizationServerConfigurerAdapter {
        private final SecurityConfigBean securityConfigBean;
        private final UserService userService;

        public AuthServerOAuth2Config(SecurityConfigBean securityConfigBean, UserService userService) {
            this.securityConfigBean = securityConfigBean;
            this.userService = userService;
        }

        @Override
        public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
            security.allowFormAuthenticationForClients();
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients.inMemory()
                .withClient(securityConfigBean.getClient())
                .secret(passwordEncoder().encode(securityConfigBean.getSecret()))
                .accessTokenValiditySeconds(securityConfigBean.getAccessTokenValiditySeconds())
                .refreshTokenValiditySeconds(securityConfigBean.getRefreshTokenValiditySeconds())
                .scopes("trust")
                .authorizedGrantTypes("password", "refresh_token");
        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints.tokenServices(tokenServices())
                .authenticationManager(authenticationManager())
                .userDetailsService(userService)
                .reuseRefreshTokens(false);
        }
    }

    @Configuration
    @EnableResourceServer
    public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
        @Value("${spring.security.resourceId}")
        private String resourceId;

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) {
            resources.resourceId(resourceId);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests().antMatchers(EVERYBODY_ALLOWED_URLS).permitAll()
                .and().authorizeRequests().anyRequest().authenticated()
                .and().csrf().disable();
        }
    }
}

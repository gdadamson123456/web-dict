package com.kostenko.webmydictionary.configuration.spring.conf;

import com.kostenko.webmydictionary.controllers.users.utils.CustomAuthenticationProvider;
import com.kostenko.webmydictionary.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collections;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.kostenko.webmydictionary.utils.Constants.ROLE_ADMIN;
import static com.kostenko.webmydictionary.utils.Constants.ROLE_USER;
import static java.lang.String.format;

@Slf4j
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final UserService userService;

    @Autowired
    public WebSecurityConfiguration(UserDetailsService userDetailsService, UserService userService) {
        checkNotNull(userDetailsService, "UserDetailService can't be null");
        checkNotNull(userService, "UserService can't be null");
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    @Bean
    public AuthenticationProvider customAuthenticationProvider() {
        return new CustomAuthenticationProvider(userService, userDetailsService);
    }

    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return new ProviderManager(Collections.singletonList(customAuthenticationProvider()));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/**").access(format("hasRole('%s')", ROLE_ADMIN))
                .antMatchers("/user/**").access(format("hasAnyRole('%s', '%s')", ROLE_USER, ROLE_ADMIN))
                .antMatchers("/logingoogle/**").permitAll()
                .and()
                .formLogin()
                .loginPage("/log")
                .defaultSuccessUrl("/")
                .failureUrl("/log?error")
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .and()
                .csrf().disable();
    }

    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return userDetailsService;
    }
}

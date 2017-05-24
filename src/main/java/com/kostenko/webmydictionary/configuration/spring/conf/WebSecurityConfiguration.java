package com.kostenko.webmydictionary.configuration.spring.conf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.kostenko.webmydictionary.utils.Constants.ROLE_ADMIN;
import static com.kostenko.webmydictionary.utils.Constants.ROLE_USER;
import static java.lang.String.format;

@Slf4j
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final AuthenticationProvider authProvider;

    @Autowired
    public WebSecurityConfiguration(UserDetailsService userDetailsServiceImpl, AuthenticationProvider authProvider) {
        checkNotNull(userDetailsServiceImpl, "UserDetailService can't be null");
        checkNotNull(authProvider, "authProvider can't be null");
        this.userDetailsService = userDetailsServiceImpl;
        this.authProvider = authProvider;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        checkNotNull(auth, "AuthenticationManagerBuilder can't be null");
        auth.authenticationProvider(authProvider);
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
//                .antMatchers("/admin/**").access(format("hasRole('%s')", ROLE_ADMIN))
//                .antMatchers("/user/**").access(format("hasAnyRole('%s', '%s')", ROLE_USER, ROLE_ADMIN))
                .antMatchers("/tokensignin/**").permitAll()
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

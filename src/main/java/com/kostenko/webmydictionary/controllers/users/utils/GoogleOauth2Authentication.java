package com.kostenko.webmydictionary.controllers.users.utils;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.kostenko.webmydictionary.dao.domain.users.User;
import com.kostenko.webmydictionary.services.RoleService;
import com.kostenko.webmydictionary.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.kostenko.webmydictionary.utils.Constants.ROLE_USER;

@Slf4j
public class GoogleOauth2Authentication {
    private static final String GOOGLE_REGISTERED_APP_USER_ID = "976118425813-o3mt3u1t3navdle20s4u8g081ichch7d.apps.googleusercontent.com";
    private final UserService userService;
    private final RoleService roleService;
    private final AuthenticationProvider authenticationProvider;

    public GoogleOauth2Authentication(UserService userService, RoleService roleService, AuthenticationProvider authenticationProvider) {
        checkNotNull(userService, "UserService can't be null");
        checkNotNull(roleService, "RoleService can't be null");
        checkNotNull(authenticationProvider, "AuthenticationProvider can't be null");
        this.userService = userService;
        this.roleService = roleService;
        this.authenticationProvider = authenticationProvider;
    }

    public Authentication authenticate(String oauthToken) throws GeneralSecurityException, IOException {
        log.debug("Oauth2 token: %s", oauthToken);
        GoogleIdTokenVerifier googleIdTokenVerifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
                .setAudience(Collections.singletonList(GOOGLE_REGISTERED_APP_USER_ID))
                .build();
        GoogleIdToken googleIdToken = googleIdTokenVerifier.verify(oauthToken);

        if (googleIdToken == null) {
            log.warn("Invalid GoogleIdToken token.");
            return null;
        }

        GoogleIdToken.Payload payload = googleIdToken.getPayload();
        String userId = payload.getSubject();
        String email = payload.getEmail();
        boolean emailVerified = payload.getEmailVerified();

        if (!emailVerified) {
            log.warn("Email is not verified");
            return null;
        }

        User user = getAuthenticatedUser(userId, email);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getLogin(), user.getPassword());
        Authentication authentication = authenticationProvider.authenticate(authenticationToken);

        if (authentication == null) {
            log.warn("User is not authenticated");
            return null;
        }

        return authentication;
    }

    private User getAuthenticatedUser(String userId, String email) {
        User user = userService.findByEmail(email);
        if (user == null) {
            user = new User(email.substring(0, email.indexOf("@")), String.valueOf(RandomUtils.nextLong()), email, roleService.findByName(ROLE_USER), userId);
            userService.create(user);
        }
        return user;
    }

    public static void putAuthenticationToSession(HttpSession session, Authentication authentication) {
        checkNotNull(session, "HttpServletRequest can't be null");
        if (authentication == null) {
            log.warn("User is not authenticated");
            return;
        }
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
    }
}

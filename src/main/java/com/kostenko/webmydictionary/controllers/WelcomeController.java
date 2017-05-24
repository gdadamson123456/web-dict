package com.kostenko.webmydictionary.controllers;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.kostenko.webmydictionary.controllers.users.utils.UserDetailsServiceImpl;
import com.kostenko.webmydictionary.dao.domain.users.User;
import com.kostenko.webmydictionary.services.RoleService;
import com.kostenko.webmydictionary.services.UserService;
import com.kostenko.webmydictionary.utils.Constants;
import com.kostenko.webmydictionary.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.security.GeneralSecurityException;
import java.util.Collections;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.kostenko.webmydictionary.utils.Constants.ROLE_USER;

@Slf4j
@Controller
@SessionAttributes({"sessionUser"})
public class WelcomeController implements Serializable {
    private static final long serialVersionUID = 3954475661965470388L;
    private static final String USER_ID = "976118425813-o3mt3u1t3navdle20s4u8g081ichch7d.apps.googleusercontent.com";
    private final Utils utils;
    private final UserService userService;
    private final RoleService roleService;
    private final AuthenticationProvider provider;
    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public WelcomeController(Utils utils, UserService userService, RoleService roleService, AuthenticationProvider provider, UserDetailsServiceImpl userDetailsService) {
        checkNotNull(utils, "Utils can't be null");
        checkNotNull(userService, "userService can't be null");
        checkNotNull(roleService, "roleService can't be null");
        checkNotNull(provider, "provider can't be null");
        checkNotNull(userDetailsService, "UserDetailsService can't be null");
        this.utils = utils;
        this.userService = userService;
        this.roleService = roleService;
        this.provider = provider;
        this.userDetailsService = userDetailsService;
    }

    @RequestMapping(Constants.Controller.Path.APP_ROOT)
    public String index(Model model) {
        User user = utils.getUser();
        if (user != null) {
            return getUserHomeLink(user, model);
        }
        return Constants.Controller.RedirectionTo.LOGIN;
    }

    private String getUserHomeLink(User user, Model model) {
        model.addAttribute("sessionUser", user);
        if (Constants.ROLE_ADMIN.equals(user.getRole().getName())) {
            return Constants.Controller.RedirectionTo.ADMIN;
        } else {
            return Constants.Controller.RedirectionTo.USER;
        }
    }

    @RequestMapping(value = "tokensignin", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String singInOuth(@RequestParam("idtoken") String idTokenString, Model model, HttpServletRequest request) throws IOException, GeneralSecurityException {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
                .setAudience(Collections.singletonList(USER_ID))
                .build();
        GoogleIdToken idToken = verifier.verify(idTokenString);
        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();
            String userId = payload.getSubject();
            String email = payload.getEmail();
            boolean emailVerified = payload.getEmailVerified();
            if (emailVerified) {
                User user = userService.findByEmail(email);
                if (user == null) {
                    user = new User(email.substring(0, email.indexOf("@")), String.valueOf(RandomUtils.nextLong()), email, roleService.findByName(ROLE_USER), userId);
                    userService.create(user);
                }
                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user.getLogin(), user.getPassword());
                // Authenticate the user
                Authentication authentication = provider.authenticate(authRequest);
                if (authentication != null) {
                    SecurityContext securityContext = SecurityContextHolder.getContext();
                    securityContext.setAuthentication(authentication);
                    // Create a new session and add the security context.
                    HttpSession session = request.getSession(true);
                    session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
                    return getUserHomeLink(user, model);
                }
//                try {//Working but strange way
//                    request.login(user.getLogin(), user.getPassword());
//                } catch (ServletException e) {
//                    e.printStackTrace();
//                }
            }
        } else {
            System.out.println("Invalid ID token.");
        }
        return Constants.View.ADMIN_ANGULAR;
    }
}

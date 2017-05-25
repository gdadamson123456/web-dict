package com.kostenko.webmydictionary.controllers;

import com.kostenko.webmydictionary.controllers.users.utils.ControllerUtils;
import com.kostenko.webmydictionary.controllers.users.utils.GoogleOauth2Authentication;
import com.kostenko.webmydictionary.dao.domain.users.User;
import com.kostenko.webmydictionary.services.RoleService;
import com.kostenko.webmydictionary.services.UserService;
import com.kostenko.webmydictionary.utils.Constants;
import com.kostenko.webmydictionary.utils.SecurityContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.security.GeneralSecurityException;

import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
@Controller
@SessionAttributes({"sessionUser"})
public class WelcomeController implements Serializable {
    private static final long serialVersionUID = 3954475661965470388L;
    private final SecurityContextUtils securityContextUtils;
    private final UserService userService;
    private final RoleService roleService;
    private final AuthenticationProvider provider;

    @Autowired
    public WelcomeController(SecurityContextUtils securityContextUtils, UserService userService, RoleService roleService, AuthenticationProvider provider) {
        checkNotNull(securityContextUtils, "Utils can't be null");
        checkNotNull(userService, "userService can't be null");
        checkNotNull(roleService, "roleService can't be null");
        checkNotNull(provider, "provider can't be null");
        this.securityContextUtils = securityContextUtils;
        this.userService = userService;
        this.roleService = roleService;
        this.provider = provider;
    }

    @RequestMapping(Constants.Controller.Path.APP_ROOT)
    public String index(Model model) {
        User user = securityContextUtils.getAuthenticatedUser();
        if (user != null) {
            return ControllerUtils.getUserHomeLink(user, model);
        }
        return Constants.Controller.RedirectionTo.LOGIN;
    }

    @RequestMapping(value = "logingoogle", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String googleSingInOauth(@RequestParam("idtoken") String idtoken, Model model, HttpServletRequest request) throws IOException, GeneralSecurityException {
        Authentication authentication = new GoogleOauth2Authentication(userService, roleService, provider).authenticate(idtoken);
        GoogleOauth2Authentication.putAuthenticationToSession(request.getSession(true), authentication);
        return ControllerUtils.getUserHomeLink(userService.findByLogin(authentication.getName()),model);
    }

}

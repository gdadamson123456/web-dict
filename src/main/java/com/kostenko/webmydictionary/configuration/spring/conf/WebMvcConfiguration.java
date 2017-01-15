package com.kostenko.webmydictionary.configuration.spring.conf;

import com.kostenko.webmydictionary.configuration.AppConfigLoader;
import com.kostenko.webmydictionary.configuration.AppConfigLoader.Property;
import com.kostenko.webmydictionary.dao.domain.users.Role;
import com.kostenko.webmydictionary.dao.domain.users.User;
import com.kostenko.webmydictionary.services.RoleService;
import com.kostenko.webmydictionary.services.UserService;
import com.kostenko.webmydictionary.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
@Configuration
@EnableWebMvc
@ComponentScan({"com.kostenko.webmydictionary"})
@PropertySource({"classpath:app.properties"})
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {
    private final UserService userService;
    private final RoleService roleService;
    private final AppConfigLoader appConfigLoader;

    @Autowired
    public WebMvcConfiguration(UserService userService, RoleService roleService, AppConfigLoader appConfigLoader) {
        checkNotNull(userService, "UserService can't be null");
        checkNotNull(roleService, "RoleService can't be null");
        checkNotNull(roleService, "AppConfigLoader can't be null");
        this.userService = userService;
        this.roleService = roleService;
        this.appConfigLoader = appConfigLoader;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("css/**").addResourceLocations("/css/").setCachePeriod(31556926);
        registry.addResourceHandler("js/**").addResourceLocations("/js/").setCachePeriod(31556926);
        registry.addResourceHandler("angular/**").addResourceLocations("/angular/").setCachePeriod(31556926);
    }

    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix(Constants.Controller.Path.APP_ROOT);
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @EventListener
    public void initDbData(ContextRefreshedEvent event) {
        checkNotNull(event, "ContextRefreshedEvent is null and data can't be put into DB");
        log.info("Trying to insert data into database regarding user and roles on the app startup");
        createData();
    }

    private void createData() {
        createStandardRoles();
        createAdminUser();
    }

    private void createStandardRoles() {
        log.info("Trying to create admin role ...");
        if (roleService.findByName(Constants.ROLE_ADMIN) == null) {
            Role role = new Role(Constants.ROLE_ADMIN);
            role.setId(String.valueOf(1));
            roleService.create(role);
            log.info("Admin role created!");
        } else {
            log.info("Admin role had been already created before");
        }
        log.info("Trying to create user role ...");
        if (roleService.findByName(Constants.ROLE_USER) == null) {
            Role role = new Role(Constants.ROLE_USER);
            role.setId(String.valueOf(2));
            roleService.create(role);
            log.info("User role created!");
        } else {
            log.info("User role had been already created before");
        }
    }

    private void createAdminUser() {
        final String email = appConfigLoader.getProperty(Property.USER_ADMIN_EMAIL);
        log.info("Trying to create admin user...");
        if (userService.findByEmail(email) == null) {
            final String login = appConfigLoader.getProperty(Property.USER_ADMIN_LOGIN);
            final String passwordEncoded = appConfigLoader.getProperty(Property.USER_ADMIN_PASS);
            final String password = getDecodedBase64String(passwordEncoded);
            User user = new User(login, password, email, roleService.findByName(Constants.ROLE_ADMIN));
            user.setId(String.valueOf(1));
            userService.create(user);
            log.info("Admin user created");
        } else {
            log.info("Admin user had been already created before");
        }
    }

    private String getDecodedBase64String(final String encoded) {
        checkNotNull(encoded, "String for decoding can't be null");
        String decoded = null;
        if (Base64.isBase64(encoded)) {
            byte[] outputBytes = Base64.decodeBase64(encoded);
            decoded = StringUtils.newStringUtf8(outputBytes);
        }
        checkNotNull(decoded, String.format("Decoder returned null for string:%s", encoded));
        return decoded;
    }
}

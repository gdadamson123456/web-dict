package com.kostenko.webmydictionary.configuration.spring.conf;

import net.tanesha.recaptcha.ReCaptchaImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc
@ComponentScan({"com.kostenko.webmydictionary"})
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("css/**").addResourceLocations("/css/").setCachePeriod(31556926);
        registry.addResourceHandler("js/**").addResourceLocations("/js/").setCachePeriod(31556926);
    }

    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Bean
    public ReCaptchaImpl reCaptcha() {
        ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
        reCaptcha.setPrivateKey("6LcskR8TAAAAAOPEUfFbTrUMbEAAmLPPCUfYX5xF");//TODO: move to the property file
        return reCaptcha;
    }
}

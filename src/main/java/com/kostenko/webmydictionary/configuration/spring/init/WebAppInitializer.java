package com.kostenko.webmydictionary.configuration.spring.init;

import com.kostenko.webmydictionary.configuration.spring.conf.WebMvcConfiguration;
import com.kostenko.webmydictionary.utils.Constants;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{WebMvcConfiguration.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{WebMvcConfiguration.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{Constants.View.Path.APP_ROOT};
    }
}

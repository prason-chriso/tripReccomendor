package com.example.config;

import org.springframework.lang.Nullable;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class DispatcherServletConfig extends AbstractAnnotationConfigDispatcherServletInitializer{
    @Nullable
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{RootConfiguration.class};
    }

    @Nullable
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{ServletConfiguration.class};
    }

    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}

package com.igitras.hikari.config.web;

import com.igitras.hikari.config.AppProperties;
import com.igitras.hikari.utils.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.support.ErrorPageFilter;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ServletWebArgumentResolverAdapter;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Web application configuration.
 *
 * @author mason
 */
@Configuration
@Component
public class WebConfiguration extends WebMvcConfigurerAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(WebConfiguration.class);

    @Autowired
    private ConfigurableApplicationContext context;

    @Autowired
    private AppProperties properties;

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUseRegisteredSuffixPatternMatch(true);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/index.html");
    }

    @Override
    public Validator getValidator() {
        return super.getValidator();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        try {
            File downloadFolder = FileUtil.resolveFolder(properties.getDownloadFolder());
            registry.addResourceHandler("/**/*.*")
                    .addResourceLocations("file:" + downloadFolder.getCanonicalPath() + "/");
        } catch (IOException e) {
            LOG.error("No valid download data folder configured.");
            context.close();
        }
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**");
    }
}


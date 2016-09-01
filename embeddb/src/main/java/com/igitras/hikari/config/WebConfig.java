//package com.igitras.hikari.config;
//
//import org.h2.server.web.WebServlet;
//import org.springframework.boot.web.servlet.ServletRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//
///**
// * Created by mason on 9/1/16.
// */
//@Configuration
//public class WebConfig {
//
//    @Bean
//    public ServletRegistrationBean h2servletRegistration() {
//        ServletRegistrationBean registration = new ServletRegistrationBean(new WebServlet());
//        registration.addUrlMappings("/console/*");
//        registration.addInitParameter("webAllowOthers", "true");
//        return registration;
//    }
//}

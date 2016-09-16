package com.igitras.hikari.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ContextLifecycleScheduledTaskRegistrar;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import javax.servlet.Filter;

/**
 * Application configuration. Add ETag supports.
 *
 * @author mason
 */
@Configuration
@EnableScheduling
public class AppConfiguration {

    @Bean
    public Filter shallowEtagHeaderFilter() {
        return new ShallowEtagHeaderFilter();
    }


    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(3);
        taskScheduler.setThreadGroupName("TaskScheduler");
        taskScheduler.setThreadNamePrefix("TaskScheduler-Th-");
        return taskScheduler;
    }

    @Bean
    public ContextLifecycleScheduledTaskRegistrar taskRegistrar() {
        ContextLifecycleScheduledTaskRegistrar scheduledTaskRegistrar =
                new ContextLifecycleScheduledTaskRegistrar();
        scheduledTaskRegistrar.setScheduler(taskScheduler());
        return scheduledTaskRegistrar;
    }
}

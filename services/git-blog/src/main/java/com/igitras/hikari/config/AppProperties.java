package com.igitras.hikari.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;

import javax.validation.constraints.NotNull;

/**
 * Application property Configuration.
 *
 * @author mason
 */
@Component
@ConfigurationProperties(prefix = "project")
public class AppProperties {

    private CorsConfiguration cors = new CorsConfiguration();

    @NotNull
    private String downloadFolder = "file:./data";

    public CorsConfiguration getCors() {
        return cors;
    }

    public AppProperties setCors(CorsConfiguration cors) {
        this.cors = cors;
        return this;
    }

    public String getDownloadFolder() {
        return downloadFolder;
    }

    public AppProperties setDownloadFolder(String downloadFolder) {
        this.downloadFolder = downloadFolder;
        return this;
    }
}

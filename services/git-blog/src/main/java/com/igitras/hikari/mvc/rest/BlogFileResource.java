package com.igitras.hikari.mvc.rest;

import static org.aspectj.weaver.tools.cache.SimpleCacheFactory.path;
import static org.springframework.web.servlet.HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE;

import com.igitras.hikari.config.AppProperties;
import com.igitras.hikari.service.BlogItem;
import com.igitras.hikari.service.FileService;
import com.igitras.hikari.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 * Blog File resource.
 *
 * @author mason
 */
@RestController
@RequestMapping(value = "blog-files")
public class BlogFileResource {
    @Autowired
    private FileService fileService;

    @Autowired
    private AppProperties properties;

    @GetMapping("**")
    public List<BlogItem> loadBlogFiles(HttpServletRequest request) throws FileNotFoundException {
        String mappingPattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        String mapping = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String path = new AntPathMatcher().extractPathWithinPattern(mappingPattern, mapping);
        File directory = FileUtil.resolveFolder(properties.getDownloadFolder());
        return fileService.loadFiles(directory, path);
    }
}

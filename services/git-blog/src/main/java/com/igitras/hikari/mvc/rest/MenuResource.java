package com.igitras.hikari.mvc.rest;

import com.igitras.hikari.config.AppProperties;
import com.igitras.hikari.service.MenuItem;
import com.igitras.hikari.service.MenuService;
import com.igitras.hikari.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Menu Resources.
 *
 * @author mason
 */
@RestController
@RequestMapping(value = "menus")
public class MenuResource {

    @Autowired
    private MenuService menuService;

    @Autowired
    private AppProperties properties;

    @GetMapping
    public MenuItem loadMenu() throws FileNotFoundException {
        File directory = FileUtil.resolveFolder(properties.getDownloadFolder());
        return menuService.loadMenuItem(directory);
    }
}

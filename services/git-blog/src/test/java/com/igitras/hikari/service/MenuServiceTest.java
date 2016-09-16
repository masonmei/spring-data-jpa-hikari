package com.igitras.hikari.service;

import org.junit.Test;

import java.io.File;
import java.util.Collection;

/**
 * Test cases for class .
 *
 * @author mason
 */
public class MenuServiceTest {
    @Test
    public void loadMenuableDirectories() throws Exception {
        String path = "/Users/mason/Individual/demos/demo-spring-data-jpa-hikari/data/";
        Collection<String> strings = new MenuService().loadMenuableDirectories(new File(path));
        System.out.println(strings);
    }

    @Test
    public void buildMenu() throws Exception {
        String path = "/Users/mason/Individual/demos/demo-spring-data-jpa-hikari/data/";
        MenuService menuService = new MenuService();
        Collection<String> strings = menuService.loadMenuableDirectories(new File(path));
        MenuItem menuItem = menuService.buildMenu(strings);
        System.out.println(menuItem);
    }

}
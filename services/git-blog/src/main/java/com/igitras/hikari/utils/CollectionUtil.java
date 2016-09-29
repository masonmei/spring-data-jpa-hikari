package com.igitras.hikari.utils;

import org.springframework.util.Assert;

import java.util.Collection;
import java.util.stream.Stream;

/**
 * Collection Utils for git blog.
 *
 * @author mason
 */
public abstract class CollectionUtil {

    /**
     * Check whether the given element is contained in the given scopes.
     *
     * @param element element to checking
     * @param scopes  scopes to checking element
     * @param <T>     element type
     * @return checking result.
     */
    public static <T> boolean contains(T element, Collection<T> scopes) {
        Assert.notNull(element, "Element to checking must not be null.");
        Assert.notNull(scopes, "Checking scopes must not be null.");
        return scopes.contains(element);
    }

    /**
     * Check whether the given element is contained in the given scopes.
     *
     * @param element element to checking
     * @param scopes  scopes to checking element
     * @param <T>     element type
     * @return checking result.
     */
    public static <T> boolean contains(T element, T... scopes) {
        Assert.notNull(element, "Element to checking must not be null.");
        Assert.notNull(scopes, "Checking scopes must not be null.");
        return Stream.of(scopes)
                .filter(t -> t.equals(element))
                .findFirst()
                .isPresent();
    }
}

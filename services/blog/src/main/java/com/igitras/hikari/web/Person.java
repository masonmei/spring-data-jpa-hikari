package com.igitras.hikari.web;

import java.io.Serializable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Person DTO.
 *
 * @author mason
 */
public class Person implements Serializable {
    private static final long serialVersionUID = -3454758774684124324L;

    private Long id;

    @NotNull
    private String name;

    @Min(value = 10)
    private int age;

    private String brief;

    public Long getId() {
        return id;
    }

    public Person setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Person setName(String name) {
        this.name = name;
        return this;
    }

    public int getAge() {
        return age;
    }

    public Person setAge(int age) {
        this.age = age;
        return this;
    }

    public String getBrief() {
        return brief;
    }

    public Person setBrief(String brief) {
        this.brief = brief;
        return this;
    }
}

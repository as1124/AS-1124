package com.as1124.ch13test.model;

import java.io.Serializable;

/**
 * 通过{@link Serializable}方式实现序列化以便对象能通过Intent传递
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class Person implements Serializable {

    private String name;

    private int age;

    public Person() {
        // default constructor
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

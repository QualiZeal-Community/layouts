package com.qualizeal.community.layouts.exceptions;

public class SectionNotFoundException extends RuntimeException {
    public SectionNotFoundException(String section) {
        super("Section not found! Please check the yaml layout for " + section);
    }
}

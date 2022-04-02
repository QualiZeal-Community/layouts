package com.qualizeal.community.layouts.exceptions;

public class LocatorNotFoundException extends RuntimeException {
    public LocatorNotFoundException(String section, String name) {
        super(name+" not found in " + section);
    }
}

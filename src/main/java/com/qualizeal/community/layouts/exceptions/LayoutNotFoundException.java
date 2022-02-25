package com.qualizeal.community.layouts.exceptions;

public class LayoutNotFoundException extends RuntimeException {
    public LayoutNotFoundException(String layout, String yaml) {
        super(layout + " not found in " + yaml);
    }
}

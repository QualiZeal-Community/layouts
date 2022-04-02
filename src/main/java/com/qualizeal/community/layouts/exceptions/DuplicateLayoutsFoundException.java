package com.qualizeal.community.layouts.exceptions;

public class DuplicateLayoutsFoundException extends RuntimeException{
    public DuplicateLayoutsFoundException(String layout) {
        super(layout + " defined in multiple yaml files.");
    }
}

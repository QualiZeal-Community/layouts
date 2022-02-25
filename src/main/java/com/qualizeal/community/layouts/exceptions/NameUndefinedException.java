package com.qualizeal.community.layouts.exceptions;

public class NameUndefinedException extends RuntimeException{
    public NameUndefinedException() {
        super("Name is not defined for locator");
    }
}

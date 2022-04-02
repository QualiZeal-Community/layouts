package com.qualizeal.community.layouts.enums;

public enum Types {
    WEB("web"),
    MOBILE("mobile"),
    WINDOWS("windows"),
    IOS("ios"),
    ANDROID("android");

    private final String type;

    Types(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}

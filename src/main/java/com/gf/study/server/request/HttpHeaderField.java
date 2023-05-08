package com.gf.study.server.request;

public class HttpHeaderField {
    private final String name;
    private final String value;

    public HttpHeaderField(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}

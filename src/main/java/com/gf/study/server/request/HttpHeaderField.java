package com.gf.study.server.request;

public class HttpHeaderField {
    private final String name;
    private final String value;
    private HttpHeaderField httpRecordField;

    public HttpHeaderField(String name, String value) {
        this.name = name;
        this.value = value;
        this.httpRecordField = null;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public HttpHeaderField getHttpRecordField() {
        return httpRecordField;
    }

    public void setHttpRecordField(HttpHeaderField httpRecordField) {
        this.httpRecordField = httpRecordField;
    }
}

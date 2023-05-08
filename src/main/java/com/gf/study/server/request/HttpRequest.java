package com.gf.study.server.request;

import com.gf.study.server.exception.IllegalRequestLineException;

import java.util.ArrayList;
import java.util.List;

public class HttpRequest {
    private int protocolMinorVersion;
    private String method;
    private String path;
    private List<HttpHeaderField> httpHeaderFieldList = new ArrayList<>();
    private String body;
    private long length;

    public int getProtocolMinorVersion() {
        return protocolMinorVersion;
    }

    public void setProtocolMinorVersion(int protocolMinorVersion) {
        this.protocolMinorVersion = protocolMinorVersion;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<HttpHeaderField> getHttpHeaderFieldList() {
        return httpHeaderFieldList;
    }

    public void setHttpHeaderField(List<HttpHeaderField> httpHeaderFieldList) {
        this.httpHeaderFieldList = httpHeaderFieldList;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public void setRequestLine(String requestLine) throws IllegalRequestLineException {
        String[] requestLineParts = requestLine.split(" ");

        if (requestLineParts.length == 3) {
            String method = requestLineParts[0].toUpperCase();
            if (!HttpMethod.isValid(method)) {
                throw new IllegalRequestLineException();
            }
            this.method = method;

            // TODO: パスのチェックが必要
            String path = requestLineParts[1];
            this.path = path;

            String httpVersion = requestLineParts[2];
            if (!httpVersion.contains("HTTP/1.")) {
                throw new IllegalRequestLineException();
            }

            try {
                int minorVersion = Integer.valueOf(httpVersion.replace("HTTP/1.", ""));
                this.protocolMinorVersion = minorVersion;
            } catch (NumberFormatException e) {
                throw new IllegalRequestLineException();
            }

            return;
        }

        throw new IllegalRequestLineException();
    }
}

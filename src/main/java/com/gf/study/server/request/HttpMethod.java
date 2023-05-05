package com.gf.study.server.request;

public enum HttpMethod {
    GET, POST, PUT, DELETE, HEAD;

    /**
     * Checks if the specified string is a valid HTTP method.
     *
     * @param method the string to check
     * @return true if the string is a valid HTTP method, false otherwise
     */
    public static boolean isValid(String method) {
        for (HttpMethod httpMethod : HttpMethod.values()) {
            if (httpMethod.name().equalsIgnoreCase(method)) {
                return true;
            }
        }
        return false;
    }
}

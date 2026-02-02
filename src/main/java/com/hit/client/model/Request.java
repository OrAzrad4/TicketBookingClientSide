package com.hit.client.model;

import java.io.Serializable;
import java.util.Map;

public class Request implements Serializable {
    private Map<String, String> headers;
    private Object body;

    public Request(Map<String, String> headers, Object body) {
        this.headers = headers;
        this.body = body;
    }
}
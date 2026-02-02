package com.hit.client.model;

import java.io.Serializable;
import java.util.Map;

public class Response implements Serializable {
    private Map<String, String> headers;
    private Object body;

    public Object getBody() {
        return body;
    }
}
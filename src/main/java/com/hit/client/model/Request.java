package com.hit.client.model;

import java.util.Map;

public class Request<T>  {

    // Action to do
    private Map<String, String> headers;

    // The members (Ticket, ID, Name)
    private T body;

    // Default Constructor
    public Request() {
    }

    // Full Constructor
    public Request(Map<String, String> headers, T body) {
        this.headers = headers;
        this.body = body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Request{" +
                "headers=" + headers +
                ", body=" + body +
                '}';
    }
}
package com.hit.client.model;

import java.io.Serializable;
import java.util.Map;

/**
 * Represents the request protocol sent from Client to Server.
 * Supports Generic Body <T> and Headers for metadata.
 */
public class Request<T> implements Serializable {

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
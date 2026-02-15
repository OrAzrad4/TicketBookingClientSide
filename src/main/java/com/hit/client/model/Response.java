package com.hit.client.model;

import java.io.Serializable;
import java.util.Map;

/**
 * Represents the response protocol sent from Server back to Client.
 * Wraps the result data with metadata headers.
 */
public class Response<T> implements Serializable {

    // Metadata ("action": "ticket/save", "status": "OK")
    private Map<String, String> headers;

    // The result data (Ticket object, List of Tickets, or String message)
    private T body;

    // Default Constructor
    public Response() {
    }

    // Full Constructor
    public Response(Map<String, String> headers, T body) {
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
        return "Response{" +
                "headers=" + headers +
                ", body=" + body +
                '}';
    }
}
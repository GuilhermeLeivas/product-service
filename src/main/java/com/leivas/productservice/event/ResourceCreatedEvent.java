package com.leivas.productservice.event;

import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletResponse;

public class ResourceCreatedEvent extends ApplicationEvent {

    private HttpServletResponse response;
    private String id;

    public ResourceCreatedEvent(Object source, HttpServletResponse response, String id) {
        super(source);
        this.response = response;
        this.id = id;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public String getId() {
        return id;
    }
}

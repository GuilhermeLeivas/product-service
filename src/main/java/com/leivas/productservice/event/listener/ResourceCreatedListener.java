package com.leivas.productservice.event.listener;

import com.leivas.productservice.event.ResourceCreatedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.net.URI;

@Component
public class ResourceCreatedListener implements ApplicationListener<ResourceCreatedEvent> {


    @Override
    public void onApplicationEvent(ResourceCreatedEvent resourceCreatedEvent) {

        adicionarHeaderLocation(resourceCreatedEvent.getResponse(),
                                resourceCreatedEvent.getId());

    }

    private void adicionarHeaderLocation(@NotNull HttpServletResponse response, String id) {

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(id).toUri();

        response.setHeader("location", uri.toASCIIString());
    }
}

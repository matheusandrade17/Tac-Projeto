package com.gestao.academico.messaging;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class InMemoryEventPublisher {
    private final List<Object> events = new CopyOnWriteArrayList<>();

    public void publish(Object event) {
        events.add(event);
        System.out.println("EVENT PUBLISHED: " + event.getClass().getSimpleName());
    }

    public List<Object> getEvents() {
        return Collections.unmodifiableList(events);
    }
}

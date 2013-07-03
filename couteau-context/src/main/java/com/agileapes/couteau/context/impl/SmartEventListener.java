package com.agileapes.couteau.context.impl;

import com.agileapes.couteau.context.contract.Event;
import com.agileapes.couteau.context.contract.EventListener;
import com.agileapes.couteau.context.util.ClassUtils;

/**
 * This event listener is an event listener that is capable of determining whether it is interested in
 * a given event type or not.
 *
 * By passing a normal event listener as a constructor argument to this listener, the listener will be
 * enable to have the added functionality of determining whether or not it can handle certain types
 * of events.
 *
 * @see #supportsEvent(com.agileapes.couteau.context.contract.Event)
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/6/29, 14:01)
 */
public class SmartEventListener implements EventListener {

    private final EventListener<Event> delegate;
    private final Class<?> eventType;

    public SmartEventListener(EventListener<Event> delegate) {
        this.delegate = delegate;
        this.eventType = ClassUtils.resolveTypeArgument(delegate.getClass(), EventListener.class);
    }

    /**
     * Will return true if the event is supported by the wrapped listener.
     * @param event    the event to be evaluated.
     * @return {@code true} if the listener is interested in the given event
     */
    public boolean supportsEvent(Event event) {
        return eventType.isAssignableFrom(event.getClass());
    }

    /**
     * Will delegate the event and trigger the underlying listener.
     * @param event    the event being fired
     */
    @Override
    public void onEvent(Event event) {
        delegate.onEvent(event);
    }

}
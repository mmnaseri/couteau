/*
 * Copyright (c) 2013. AgileApes (http://www.agileapes.scom/), and
 * associated organization.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the Software
 * without restriction, including without limitation the rights to use, copy, modify,
 * merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 */

package com.agileapes.couteau.reflection.property.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Type;

/**
 * This implementation allows for easily extending the framework to add field-based property accessors.
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (7/9/13, 12:38 PM)
 */
public abstract class AbstractFieldPropertyAccessor<E> extends AbstractPropertyAccessor<E> {

    /**
     * the field being wrapped
     */
    private final Field field;

    /**
     * The invocation target
     */
    private Object target;

    /**
     * Instantiates the accessor, while wrapping the field and taking in the invocation target
     * @param field     the field
     * @param target    the target
     */
    public AbstractFieldPropertyAccessor(Field field, Object target) {
        this.field = field;
        this.target = target;
    }

    /**
     * @return the wrapped field
     */
    protected Field getField() {
        return field;
    }

    /**
     * @return the invocation target
     */
    protected Object getTarget() {
        return target;
    }

    /**
     * @return the type of the target
     */
    protected Class<?> getTargetType() {
        return getTarget().getClass();
    }

    /**
     * @return the current value of the field
     * @throws Exception
     */
    protected Object get() throws Exception {
        getField().setAccessible(true);
        return getField().get(getTarget());
    }

    /**
     * Updates the current value of the field
     * @param value    the new value
     * @throws Exception
     */
    protected void set(Object value) throws Exception {
        getField().setAccessible(true);
        getField().set(getTarget(), value);
    }

    /**
     * Changes the target of invocation
     * @param target    the new target
     */
    public void setTarget(Object target) {
        this.target = target;
    }

    /**
     * @return the generic type of the property being wrapped
     */
    @Override
    public Type getGenericPropertyType() {
        return getField().getGenericType();
    }

    /**
     * @return the type of the property being wrapped.
     */
    @SuppressWarnings("unchecked")
    @Override
    public Class<E> getPropertyType() {
        return (Class<E>) getField().getType();
    }

    @Override
    protected AnnotatedElement getAnnotatedElement() {
        return getField();
    }

    @Override
    protected Member getMember() {
        return getField();
    }
}

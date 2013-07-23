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

package com.agileapes.couteau.context.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class implements all the functionalities expected of a context in a manner that is
 * guaranteed to be thread-safe.
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (6/27/13, 4:33 PM)
 */
public abstract class AbstractThreadSafeContext<E> extends AbstractContext<E> {

    private final Map<String, E> map = new ConcurrentHashMap<String, E>();

    @Override
    protected boolean write(String name, E item) {
        map.put(name, item);
        return contains(name);
    }

    @Override
    protected E read(String name) {
        return map.get(name);
    }

    @Override
    public boolean contains(String name) {
        return map.containsKey(name);
    }

    @Override
    public Collection<E> getBeans() {
        return Collections.unmodifiableCollection(map.values());
    }

    @Override
    public Collection<String> getBeanNames() {
        return Collections.unmodifiableCollection(map.keySet());
    }

}

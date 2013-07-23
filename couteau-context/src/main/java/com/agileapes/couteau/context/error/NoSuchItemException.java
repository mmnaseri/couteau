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

package com.agileapes.couteau.context.error;

/**
 * This exception indicates an attempt to retrieve an item by beanName that has not been registered
 * with the registry.
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (6/27/13, 4:18 PM)
 */
public class NoSuchItemException extends RegistryException {

    private static final String MESSAGE = "No such item has been registered: %s";
    private final String beanName;

    /**
     * Instantiates the exception
     * @param beanName    the name of the bean being looked up.
     */
    public NoSuchItemException(String beanName) {
        super(String.format(MESSAGE, beanName));
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }
}

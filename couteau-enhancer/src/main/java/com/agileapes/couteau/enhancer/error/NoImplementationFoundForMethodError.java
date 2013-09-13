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

package com.agileapes.couteau.enhancer.error;

/**
 * Indicates call to a method that has not been implemented. This is most likely the
 * result of a proxy being told to invoke the super method on an interface.
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/9/10, 16:40)
 */
public class NoImplementationFoundForMethodError extends Error {

    public NoImplementationFoundForMethodError(Class<?> type, String methodName) {
        super("No implementation was found for method " + type.getCanonicalName() + "." + methodName);
    }

}
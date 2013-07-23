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
 * This is the general basis for any errors inside the value reader that are actually related to
 * the process of reading a value.
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (7/4/13, 11:33 AM)
 */
public class ValueReaderError extends RuntimeException {

    /**
     * Instantiates the exception
     * @param message    the message explaining why a fatal error has occurred
     */
    public ValueReaderError(String message) {
        super(message);
    }

    /**
     * Instantiates the exception, specifying the root cause of the error
     * @param message    the message explaining why a fatal error has occurred
     * @param cause      the cause of the error
     */
    public ValueReaderError(String message, Throwable cause) {
        super(message, cause);
    }

}

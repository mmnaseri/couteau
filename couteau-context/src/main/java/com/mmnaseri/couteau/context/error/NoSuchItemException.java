/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2013 Milad Naseri.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mmnaseri.couteau.context.error;

/**
 * This exception indicates an attempt to retrieve an item by beanName that has not been registered
 * with the registry.
 *
 * @author Milad Naseri (mmnaseri@programmer.net)
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

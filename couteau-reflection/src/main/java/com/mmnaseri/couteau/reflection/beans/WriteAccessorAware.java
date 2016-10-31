/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2013 AgileApes, Ltd.
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

package com.mmnaseri.couteau.reflection.beans;

import com.mmnaseri.couteau.reflection.error.NoSuchPropertyException;
import com.mmnaseri.couteau.reflection.property.WritePropertyAccessor;

/**
 * Classes implementing this interface are able to expose writers for the properties they are
 * working with.
 *
 * @author Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/9/13, 12:53 PM)
 */
public interface WriteAccessorAware {

    /**
     * Will return a generic property writer for the given property
     * @param propertyName    the property name
     * @return the property writer
     * @throws NoSuchPropertyException
     */
    WritePropertyAccessor<?> getPropertyWriter(String propertyName) throws NoSuchPropertyException;

    /**
     * Will return a property writer for a property of the given name-type
     * @param propertyName    the name of the property
     * @param propertyType    the type of the property
     * @param <T>             the type parameter of the property
     * @return the property writer
     * @throws NoSuchPropertyException
     */
    <T> WritePropertyAccessor<T> getPropertyWriter(String propertyName, Class<T> propertyType) throws NoSuchPropertyException;

}

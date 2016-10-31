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

package com.mmnaseri.couteau.reflection.convert.impl;

import com.mmnaseri.couteau.reflection.convert.ConversionDecision;
import com.mmnaseri.couteau.reflection.convert.ConversionStrategy;
import com.mmnaseri.couteau.reflection.property.PropertyDescriptor;
import com.mmnaseri.couteau.reflection.util.ReflectionUtils;

/**
 * @author Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/13/13, 7:36 AM)
 */
public class DefaultConversionStrategy implements ConversionStrategy {

    @Override
    public ConversionDecision decide(PropertyDescriptor propertyDescriptor) {
        final Class<?> propertyType = ReflectionUtils.getComponentType(ReflectionUtils.mapType(propertyDescriptor.getType()));
        if (propertyDescriptor.getName().equals("class") || propertyType.getCanonicalName().matches("java\\.lang\\.[^\\.]+")) {
            return ConversionDecision.PASS;
        }
        return ConversionDecision.CONVERT;
    }

}

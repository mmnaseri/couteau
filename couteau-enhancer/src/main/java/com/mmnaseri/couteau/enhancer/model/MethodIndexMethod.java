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

package com.mmnaseri.couteau.enhancer.model;

import com.mmnaseri.couteau.enhancer.api.MethodDescriptor;
import com.mmnaseri.couteau.enhancer.impl.ImmutableMethodDescriptor;
import com.mmnaseri.couteau.freemarker.api.Invokable;
import com.mmnaseri.couteau.freemarker.model.TypedMethodModel;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * This method model will associate a single, unique number to each method
 *
 * @author Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (2013/9/10, 18:40)
 */
public class MethodIndexMethod extends TypedMethodModel {

    private final Map<MethodDescriptor, Integer> indices = new HashMap<MethodDescriptor, Integer>();

    @Invokable
    public int getIndex(Method method) {
        final ImmutableMethodDescriptor descriptor = new ImmutableMethodDescriptor(method.getDeclaringClass(), method.getReturnType(), method.getName(), method.getParameterTypes(), method.getAnnotations());
        if (!indices.containsKey(descriptor)) {
            indices.put(descriptor, indices.size());
        }
        return indices.get(descriptor);
    }

}

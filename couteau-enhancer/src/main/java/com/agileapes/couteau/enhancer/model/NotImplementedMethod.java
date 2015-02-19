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

package com.agileapes.couteau.enhancer.model;

import com.agileapes.couteau.enhancer.api.MethodDescriptor;
import com.agileapes.couteau.enhancer.impl.ImmutableMethodDescriptor;
import com.agileapes.couteau.freemarker.api.Invokable;
import com.agileapes.couteau.freemarker.model.FilteringMethodModel;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * This filter will leave out any method signature that has been already implemented
 * through the generated code
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/9/10, 18:28)
 */
public class NotImplementedMethod extends FilteringMethodModel<Method> {

    private final Set<MethodDescriptor> descriptors = new HashSet<MethodDescriptor>();

    @Invokable
    @Override
    protected boolean filter(Method item) {
        final ImmutableMethodDescriptor descriptor = new ImmutableMethodDescriptor(item.getDeclaringClass(), item.getReturnType(), item.getName(), item.getParameterTypes(), item.getAnnotations());
        final boolean implemented = descriptors.contains(descriptor);
        if (!implemented) {
            descriptors.add(descriptor);
        }
        return !implemented;
    }
}

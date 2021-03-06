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

package com.mmnaseri.couteau.enhancer.impl;

import com.mmnaseri.couteau.basics.api.Cache;
import com.mmnaseri.couteau.basics.api.impl.SimpleCache;
import com.mmnaseri.couteau.enhancer.api.*;
import com.mmnaseri.couteau.enhancer.error.BeanInitializationError;
import com.mmnaseri.couteau.reflection.beans.BeanInitializer;
import com.mmnaseri.couteau.reflection.beans.impl.ConstructorBeanInitializer;
import com.mmnaseri.couteau.reflection.error.BeanInstantiationException;

/**
 * This is the default enhancer, relying on an external {@link ClassEnhancer} to provide
 * the underlying enhancing of the given class.
 *
 * @author Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (2013/9/10, 15:07)
 */
public class DefaultEnhancer<E> implements Enhancer<E> {

    private final ClassEnhancer<E> classEnhancer;
    private Class<? extends E> superClass = null;
    private BeanInitializer initializer = new ConstructorBeanInitializer();
    private MethodInterceptor interceptor;
    private final Cache<Enhancement, Class<? extends E>> enhancementCache = new SimpleCache<Enhancement, Class<? extends E>>();
    private Class[] interfaces;

    public DefaultEnhancer() {
        this(new GeneratingClassEnhancer<E>(null));
    }

    public DefaultEnhancer(ClassLoader classLoader) {
        this(new GeneratingClassEnhancer<E>(classLoader));
    }

    public DefaultEnhancer(ClassEnhancer<E> classEnhancer) {
        this.classEnhancer = classEnhancer;
        setNamingPolicy(new DefaultNamingPolicy());
    }

    @Override
    public void setSuperClass(Class<? extends E> superClass) {
        this.superClass = superClass;
        classEnhancer.setSuperClass(superClass);
    }

    @Override
    public void setInterfaces(Class[] interfaces) {
        this.interfaces = interfaces;
        this.classEnhancer.setInterfaces(interfaces);
    }

    @Override
    public void setInterceptor(MethodInterceptor interceptor) {
        this.interceptor = interceptor;
    }

    @Override
    public void setNamingPolicy(NamingPolicy namingPolicy) {
        classEnhancer.setNamingPolicy(namingPolicy);
    }

    @Override
    public E create() {
        return create(new Class[0], new Object[0]);
    }

    @Override
    public E create(Class[] argumentTypes, Object[] constructorArguments) {
        final Enhancement enhancement = new Enhancement(superClass, interfaces);
        final Class<? extends E> enhancedClass;
        if (enhancementCache.contains(enhancement)) {
            enhancedClass = enhancementCache.read(enhancement);
        } else {
            enhancedClass = classEnhancer.enhance();
            enhancementCache.write(enhancement, enhancedClass);
        }
        final E bean;
        try {
            bean = initializer.initialize(enhancedClass, argumentTypes, constructorArguments);
        } catch (BeanInstantiationException e) {
            throw new BeanInitializationError(superClass, e);
        }
        ((Interceptible) bean).setInterceptor(interceptor);
        return bean;
    }

}

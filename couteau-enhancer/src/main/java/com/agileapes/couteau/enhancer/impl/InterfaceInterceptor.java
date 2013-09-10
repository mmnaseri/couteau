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

package com.agileapes.couteau.enhancer.impl;

import com.agileapes.couteau.enhancer.api.MethodDescriptor;
import com.agileapes.couteau.enhancer.api.MethodInterceptor;
import com.agileapes.couteau.enhancer.api.MethodProxy;
import com.agileapes.couteau.enhancer.error.BeanInitializationError;
import com.agileapes.couteau.reflection.beans.BeanInitializer;
import com.agileapes.couteau.reflection.beans.impl.ConstructorBeanInitializer;
import com.agileapes.couteau.reflection.error.BeanInstantiationException;
import com.agileapes.couteau.reflection.util.assets.MemberNameFilter;
import com.agileapes.couteau.reflection.util.assets.MethodArgumentsFilter;
import com.agileapes.couteau.reflection.util.assets.MethodReturnTypeFilter;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.agileapes.couteau.reflection.util.ReflectionUtils.withMethods;

/**
 * This interceptor allows for dynamic assignment of interface implementation as delegates
 * and also delegates calls to all interfaces implemented by the callback itself.
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/9/10, 22:11)
 */
public abstract class InterfaceInterceptor implements MethodInterceptor {

    private final Map<Class, Object> interfaces = new ConcurrentHashMap<Class, Object>();
    private final BeanInitializer beanInitializer = new ConstructorBeanInitializer();

    public InterfaceInterceptor() {
        for (Class superType : getInterfaces()) {
            interfaces.put(superType, null);
        }
    }

    protected abstract Object call(MethodDescriptor methodDescriptor, Object target, Object[] arguments, MethodProxy methodProxy) throws Throwable;

    public Class[] getInterfaces() {
        return interfaces.keySet().toArray(new Class[interfaces.size()]);
    }

    @Override
    public Object intercept(MethodDescriptor methodDescriptor, Object target, Object[] arguments, MethodProxy methodProxy) throws Throwable {
        if (interfaces.containsKey(methodDescriptor.getDeclaringClass())) {
            Object targetObject = interfaces.get(methodDescriptor.getDeclaringClass());
            final Class<?> searchTarget;
            if (targetObject == null) {
                targetObject = target;
                searchTarget = getClass();
            } else {
                searchTarget = targetObject.getClass();
            }
            final Method method = withMethods(searchTarget)
                    .keep(new MethodReturnTypeFilter(methodDescriptor.getReturnType()))
                    .keep(new MemberNameFilter(methodDescriptor.getName()))
                    .keep(new MethodArgumentsFilter(methodDescriptor.getParameterTypes()))
                    .first();
            if (method != null) {
                return method.invoke(targetObject, arguments);
            }
        }
        return methodProxy.callSuper(target, arguments);
    }

    public <E> void addInterface(Class<E> contract, Class<? extends E> implementation) {
        final E concrete;
        try {
            concrete = beanInitializer.initialize(implementation, new Class[0]);
        } catch (BeanInstantiationException e) {
            throw new BeanInitializationError(implementation, e);
        }
        interfaces.put(contract, concrete);
    }

}

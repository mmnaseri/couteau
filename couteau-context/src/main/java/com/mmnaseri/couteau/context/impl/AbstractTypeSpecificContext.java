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

package com.mmnaseri.couteau.context.impl;

import com.mmnaseri.couteau.context.contract.Context;
import com.mmnaseri.couteau.context.error.InvalidBeanNameException;
import com.mmnaseri.couteau.context.error.RegistryException;
import com.mmnaseri.couteau.context.contract.OrderedBean;

/**
 * This is a thread-safe context that requires items to be named after the canonical names
 * of their classes. This ensures that singleton objects within the context are unique by
 * their types as well.
 *
 * @author Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (6/27/13, 4:35 PM)
 */
public abstract class AbstractTypeSpecificContext<E> extends AbstractThreadSafeContext<E> {

    public AbstractTypeSpecificContext() {
        addBeanProcessor(new BeanProcessorAdapter<E>(OrderedBean.HIGHEST_PRECEDENCE) {
            @Override
            public E postProcessBeforeRegistration(E bean, String name) throws RegistryException {
                if (name == null || !name.equals(bean.getClass().getCanonicalName())) {
                    throw new InvalidBeanNameException(bean.getClass().getCanonicalName(), name);
                }
                return bean;
            }
        });
    }

    @Override
    public Context<E> register(E item) throws RegistryException {
        register(item.getClass().getCanonicalName(), item);
        return this;
    }

}

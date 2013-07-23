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

package com.agileapes.couteau.reflection.beans;

/**
 * This factory takes in a bean and dispenses identifiable bean wrappers for it.
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (7/13/13, 7:08 AM)
 */
public interface BeanWrapperFactory {

    /**
     * Dispenses a bean wrapper for the given bean
     * @param bean    the bean to be wrapped
     * @param <E>     the type of the bean
     * @return the bean wrapper
     */
    <E> BeanWrapper<E> getBeanWrapper(E bean);

}
